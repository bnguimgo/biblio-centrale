package com.bnguimgo.biblio.biblocentrale.service;

import com.bnguimgo.biblio.biblocentrale.dto.BookDTO;
import com.bnguimgo.biblio.biblocentrale.entity.Book;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioException;
import com.bnguimgo.biblio.biblocentrale.mapper.DtoMapper;
import com.bnguimgo.biblio.biblocentrale.repository.AuthorRepository;
import com.bnguimgo.biblio.biblocentrale.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum.AUTHOR_NOT_FOUND;
import static com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum.BOOK_NOT_FOUND;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private DtoMapper mapper;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDTO createBook(Long authorId, BookDTO bookDTO) throws BiblioException {

        Book book = mapper.mapToBook(bookDTO);
        return authorRepository.findById(authorId).map(author -> {

            Date now = Date.from(Instant.now());
            book.setCreatedDate(now);
            book.setModifiedDate(now);
            book.setAuthor(author);

            return mapper.mapToBookDTO(bookRepository.save(book));

        }).orElseThrow(() -> new BiblioException(AUTHOR_NOT_FOUND, "Not found Author with id = " + authorId));
    }

    public Optional<BookDTO> getBookById(Long id) {
        return bookRepository.findById(id).map(mapper::mapToBookDTO);
    }

    public List<BookDTO> getAllBooks() {

        return bookRepository.findAll().stream()
                .map(mapper::mapToBookDTO).collect(Collectors.toList());
    }

    public BookDTO updateBook(Long bookId, BookDTO bookDTO) throws BiblioException {

        return bookRepository.findById(bookId).map(book -> {

            book.setTitle(bookDTO.getTitle());
            book.setIsbn(bookDTO.getIsbn());
            book.setModifiedDate(Date.from(Instant.now()));//On met uniquement à jour la date de modification du livre

            return mapper.mapToBookDTO(bookRepository.save(book));

        }).orElseThrow(() -> new BiblioException(BOOK_NOT_FOUND, "Not found Book with id = " + bookId));
    }

    public BookDTO assignBookToAuthor(Long bookId, Long authorId) throws BiblioException {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BiblioException(BOOK_NOT_FOUND, "Not found book with id = " + bookId));
        return authorRepository.findById(authorId).map(author -> {

            book.setModifiedDate(Date.from(Instant.now()));
            book.setAuthor(author);

            return mapper.mapToBookDTO(bookRepository.save(book));

        }).orElseThrow(() -> new BiblioException(AUTHOR_NOT_FOUND, "Not found Author with id = " + authorId));
    }

    public void deleteBook(Long id) throws BiblioException {

        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new BiblioException(BOOK_NOT_FOUND, "Book not found with id: " + id);
        }
    }
}