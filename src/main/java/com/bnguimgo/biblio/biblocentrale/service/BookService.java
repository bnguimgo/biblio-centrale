package com.bnguimgo.biblio.biblocentrale.service;

import com.bnguimgo.biblio.biblocentrale.dto.BookDTO;
import com.bnguimgo.biblio.biblocentrale.dto.BookStudentAssignDTO;
import com.bnguimgo.biblio.biblocentrale.dto.StudentBookDTO;
import com.bnguimgo.biblio.biblocentrale.entity.Book;
import com.bnguimgo.biblio.biblocentrale.entity.Student;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioException;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioRuntimeException;
import com.bnguimgo.biblio.biblocentrale.mapper.DtoMapper;
import com.bnguimgo.biblio.biblocentrale.repository.AuthorRepository;
import com.bnguimgo.biblio.biblocentrale.repository.BookRepository;
import com.bnguimgo.biblio.biblocentrale.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum.*;

//Source Transaction: https://www.baeldung.com/spring-vs-jta-transactional
//Source Transaction: https://vladmihalcea.com/spring-transaction-best-practices/
@Service
@Transactional(
        isolation = Isolation.READ_COMMITTED, //Ceci est l'annotation par défaut, mais qui ne règle pas complètement le problème de Lost Update (Voir les liens ci-dessus)
        propagation = Propagation.SUPPORTS,
        readOnly = true,
        timeout = 30)
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DtoMapper mapper;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Modifying
    public BookDTO createBook(Long authorId, BookDTO bookDTO) throws BiblioException {

        Book book = mapper.mapToBook(bookDTO);
        if(Objects.nonNull(book.getId())) {
            throw new BiblioException(TECHNICAL_ERROR, HttpStatus.BAD_REQUEST, "Remove book id for creation ");
        }
        return authorRepository.findById(authorId).map(author -> {

            book.setCreatedDate(LocalDateTime.now());
            book.setAuthor(author);

            return mapper.mapToBookDTO(bookRepository.save(book));

        }).orElseThrow(() -> new BiblioException(AUTHOR_NOT_FOUND, HttpStatus.NOT_FOUND, "Author not found with id = " + authorId));
    }

    public Optional<BookDTO> getBookById(Long id) {
        return bookRepository.findById(id).map(mapper::mapToBookDTO);
    }

    public List<BookDTO> getAllBooks() {

        return bookRepository.findAll().stream()
                .map(mapper::mapToBookDTO).collect(Collectors.toList());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Modifying
    public BookDTO updateBook(Long bookId, BookDTO bookDTO) throws BiblioException {

        return bookRepository.findById(bookId).map(book -> {

            book.setTitle(bookDTO.getTitle());
            book.setIsbn(bookDTO.getIsbn());
            book.setModifiedDate(LocalDateTime.now());//On met uniquement à jour la date de modification du livre

            return mapper.mapToBookDTO(bookRepository.save(book));

        }).orElseThrow(() -> new BiblioException(BOOK_NOT_FOUND, HttpStatus.NOT_FOUND, "Book not found with id = " + bookId));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Modifying
    public BookDTO assignBookToAuthor(Long bookId, Long authorId) throws BiblioException {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BiblioException(BOOK_NOT_FOUND, HttpStatus.NOT_FOUND, "Book not found with id = " + bookId));
        return authorRepository.findById(authorId).map(author -> {

            book.setModifiedDate(LocalDateTime.now());
            book.setAuthor(author);

            return mapper.mapToBookDTO(bookRepository.save(book));

        }).orElseThrow(() -> new BiblioException(AUTHOR_NOT_FOUND, HttpStatus.NOT_FOUND, "Author not found  with id = " + authorId));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Modifying
    public BookStudentAssignDTO assignBookToStudent(Long bookId, Long studentId) throws BiblioException {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BiblioException(BOOK_NOT_FOUND, HttpStatus.NOT_FOUND, "Book not found with id = " + bookId));
        return studentRepository.findById(studentId).map(student -> {

            if(student.getBooks().stream().anyMatch(book1 -> bookId.equals(book1.getId()))){
                throw new BiblioRuntimeException(BOOK_DUPLICATED_BORROW, HttpStatus.BAD_REQUEST, "Duplicated borrow book with id = " + bookId);//Interdit d'emprunter le même livre 2 fois
            }
            student.setModifiedDate(LocalDateTime.now());
            //book.getStudents().add(student); //Ceci ne marche pas
            book.addStudent(student);

            return mapper.mapToBookDTOStudent(bookRepository.save(book), studentId);

        }).orElseThrow(() -> new BiblioException(STUDENT_NOT_FOUND, HttpStatus.NOT_FOUND, "Student not found with id = " + studentId));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Modifying
    public StudentBookDTO removeBookFromStudent(Long bookId, Long studentId) throws BiblioException {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BiblioException(BOOK_NOT_FOUND, HttpStatus.NOT_FOUND, "Book not found with id = " + bookId));

        return studentRepository.findById(studentId).map(student -> {

            student.setModifiedDate(LocalDateTime.now());
            student.getBooks().stream().filter(st ->st.getId().equals(bookId)).findAny().orElseThrow(() -> new BiblioRuntimeException(BOOK_NOT_FOUND, HttpStatus.BAD_REQUEST, "Student not found with Book id = " + bookId));
            book.removeStudent(student);

            return mapper.mapToBookDTOStudent(bookRepository.save(book));

        }).orElseThrow(() -> new BiblioException(STUDENT_NOT_FOUND, HttpStatus.NOT_FOUND, "Student not found with id = " + studentId));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Modifying
    public void deleteBook(Long bookId) throws BiblioException {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BiblioException(BOOK_NOT_FOUND, HttpStatus.NOT_FOUND, "Book not found with id = " + bookId));
        if(!book.getStudents().isEmpty()) {
            throw new BiblioException(BOOK_CANNOT_DELETE, HttpStatus.BAD_REQUEST, "Cannot delete borrowed Book, please first remove books from Student with ids = "+
                    book.getStudents().stream().map(Student::getId).collect(Collectors.toSet()));
        }
        bookRepository.deleteById(bookId);
    }
}