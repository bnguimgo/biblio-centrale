package com.bnguimgo.biblio.biblocentrale.controller;

import com.bnguimgo.biblio.biblocentrale.dto.BookDTO;
import com.bnguimgo.biblio.biblocentrale.dto.BookStudentAssignDTO;
import com.bnguimgo.biblio.biblocentrale.dto.StudentBookDTO;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioException;
import com.bnguimgo.biblio.biblocentrale.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/authors/{authorId}")
    public ResponseEntity<BookDTO> createBook(@PathVariable(value = "authorId") Long authorId,
                                              @RequestBody @Valid BookDTO bookDTO) throws BiblioException {

        return new ResponseEntity<>(bookService.createBook(authorId, bookDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable(value = "bookId") Long bookId) {
        return bookService.getBookById(bookId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.FOUND);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable(value = "bookId") Long bookId,
                                              @RequestBody @Valid BookDTO bookDTO) throws BiblioException {

        return new ResponseEntity<>(bookService.updateBook(bookId, bookDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}/assign/authors/{authorId}")
    public ResponseEntity<BookDTO> assignBookToAuthor(@PathVariable(value = "bookId") Long bookId,
                                              @PathVariable(value = "authorId") Long authorId) throws BiblioException {

        return new ResponseEntity<>(bookService.assignBookToAuthor(bookId, authorId), HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}/assign/students/{studentId}")
    public ResponseEntity<BookStudentAssignDTO> assignBookToStudent(@PathVariable(value = "bookId") Long bookId,
                                                                    @PathVariable(value = "studentId") Long studentId) throws BiblioException {

        return new ResponseEntity<>(bookService.assignBookToStudent(bookId, studentId), HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}/remove/students/{studentId}")
    public ResponseEntity<StudentBookDTO> removeBookFromStudent(@PathVariable(value = "bookId") Long bookId,
                                                              @PathVariable(value = "studentId") Long studentId) throws BiblioException {
        return new ResponseEntity<>(bookService.removeBookFromStudent(bookId, studentId), HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable(value = "bookId") Long bookId) throws BiblioException {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}