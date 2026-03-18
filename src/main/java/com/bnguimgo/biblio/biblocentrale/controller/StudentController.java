package com.bnguimgo.biblio.biblocentrale.controller;

import com.bnguimgo.biblio.biblocentrale.dto.StudentDTO;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioException;
import com.bnguimgo.biblio.biblocentrale.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody @Valid StudentDTO studentDto){
        return new ResponseEntity<>(studentService.createStudent(studentDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable(value = "id") Long id, @RequestBody @Valid StudentDTO studentDto) throws BiblioException {
        return new ResponseEntity<>(studentService.updateStudent(id, studentDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) throws BiblioException {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
    
}
