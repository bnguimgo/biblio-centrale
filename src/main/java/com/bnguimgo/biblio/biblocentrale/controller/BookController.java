package com.bnguimgo.biblio.biblocentrale.controller;

import com.bnguimgo.biblio.biblocentrale.dto.BookDTO;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioException;
import com.bnguimgo.biblio.biblocentrale.service.BookService;
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
                                              @RequestBody BookDTO bookDTO) throws BiblioException {

        return new ResponseEntity<>(bookService.createBook(authorId, bookDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable(value = "bookId") Long bookId) {
        return bookService.getBookById(bookId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable(value = "bookId") Long bookId,
                                              @RequestBody BookDTO bookDTO) throws BiblioException {

        return new ResponseEntity<>(bookService.updateBook(bookId, bookDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}/assign/authors/{authorId}")
    public ResponseEntity<BookDTO> assignBookToAuthor(@PathVariable(value = "bookId") Long bookId,
                                              @PathVariable(value = "authorId") Long authorId) throws BiblioException {

        return new ResponseEntity<>(bookService.assignBookToAuthor(bookId, authorId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable(value = "bookId") Long bookId) throws BiblioException {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

}