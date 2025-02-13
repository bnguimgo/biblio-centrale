package com.bnguimgo.biblio.biblocentrale.service;

import com.bnguimgo.biblio.biblocentrale.entity.Item;
import com.bnguimgo.biblio.biblocentrale.entity.Student;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioException;
import com.bnguimgo.biblio.biblocentrale.mapper.DtoMapper;
import com.bnguimgo.biblio.biblocentrale.repository.ItemRepository;
import com.bnguimgo.biblio.biblocentrale.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum.STUDENT_NOT_FOUND;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DtoMapper mapper;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {

        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {

        Date now = Date.from(Instant.now());
        student.setCreatedDate(now);
        student.setModifiedDate(now);
        if( !CollectionUtils.isEmpty(student.getItems())) {
            student.getItems().forEach(item -> {
                item.setCreatedDate(now);
                item.setModifiedDate(now);
            });
        }
        return studentRepository.save(student);

    }

    public Student updateStudent(Long id, Student student) throws BiblioException {

        return studentRepository.findById(id).map(studentToSave -> {

            Date now = Date.from(Instant.now());
            studentToSave.setModifiedDate(now);
            studentToSave.setFirstName(student.getFirstName());
            studentToSave.setLastName(student.getLastName());
            //FIXME UPDATE THIS
/*            Set<Item> items = itemRepository.findAllByIdIn((student.getItems().stream().map(Item::getId).collect(Collectors.toList())));
            items.forEach(item ->{
                item.setItemName(student.getItems().stream().findFirst().orElseThrow(null).getItemName());
                item.setItemName(student.getItems().stream().findFirst().orElseThrow(null).getItemName());
            });
            studentToSave.setItems(items);*/

            Map<Long,Item> itemsMap = student.getItems().stream().collect(Collectors.toMap(Item::getId, Function.identity()));
            Set<Item> items = itemRepository.findAllByIdIn(itemsMap.keySet());
            items.forEach(item ->{
                item.setItemName(itemsMap.get(item.getId()).getItemName());
                item.setItemCode(itemsMap.get(item.getId()).getItemCode());
                item.setModifiedDate(Date.from(Instant.now()));
            });
            studentToSave.setItems(items);

            return studentRepository.save(studentToSave);

        }).orElseThrow(() -> new BiblioException(STUDENT_NOT_FOUND, "Student not found with id: " + id));

    }

    public void deleteStudent(Long id) throws BiblioException {

        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new BiblioException(STUDENT_NOT_FOUND, "Student not found with id: " + id);
        }
    }
}
