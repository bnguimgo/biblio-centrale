package com.bnguimgo.biblio.biblocentrale.service;

import com.bnguimgo.biblio.biblocentrale.dto.StudentDTO;
import com.bnguimgo.biblio.biblocentrale.entity.Book;
import com.bnguimgo.biblio.biblocentrale.entity.Student;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioException;
import com.bnguimgo.biblio.biblocentrale.mapper.DtoMapper;
import com.bnguimgo.biblio.biblocentrale.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum.STUDENT_CANNOT_DELETE;
import static com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum.STUDENT_NOT_FOUND;

@Service
@Transactional(
        isolation = Isolation.READ_COMMITTED, //Ceci est l'annotation par défaut, mais qui ne règle pas complètement le problème de Lost Update (Voir les liens ci-dessus)
        propagation = Propagation.SUPPORTS,
        readOnly = true,
        timeout = 30)
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    private DtoMapper mapper;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @ReadOnlyProperty
    public List<StudentDTO> getAllStudents() {

        return studentRepository.findAll().stream()
                .map(mapper::mapToStudentDTO).collect(Collectors.toList());
    }

    @ReadOnlyProperty
    public Optional<StudentDTO> getStudentById(Long id) {

        return studentRepository.findById(id).map(mapper::mapToStudentDTO);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Modifying
    public StudentDTO createStudent(StudentDTO studentDto) {

        Student student = mapper.mapToStudent(studentDto);
        student.setCreatedDate(LocalDateTime.now());
        return mapper.mapToStudentDTO(studentRepository.save(student));

    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Modifying
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) throws BiblioException {

        return studentRepository.findById(id).map(studentToSave -> {

            studentToSave.setModifiedDate(LocalDateTime.now());
            studentToSave.setFirstName(studentDTO.getFirstName());
            studentToSave.setLastName(studentDTO.getLastName());

            return mapper.mapToStudentDTO(studentRepository.save(studentToSave));

        }).orElseThrow(() -> new BiblioException(STUDENT_NOT_FOUND, HttpStatus.NOT_FOUND, "Student not found with id: " + id));

    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Modifying
    public void deleteStudent(Long studentId) throws BiblioException {

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new BiblioException(STUDENT_NOT_FOUND, HttpStatus.NOT_FOUND, "Student not found with id = " + studentId));
        if(!student.getBooks().isEmpty()) {
            throw new BiblioException(STUDENT_CANNOT_DELETE, HttpStatus.BAD_REQUEST, "Cannot delete Student with borrowed books, please first remove borrowed books ids = "+
                    student.getBooks().stream().map(Book::getId).collect(Collectors.toSet()) + " from Student");
        }
        studentRepository.deleteById(studentId);
    }
}
