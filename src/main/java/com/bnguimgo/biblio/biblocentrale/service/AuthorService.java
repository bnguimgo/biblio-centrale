package com.bnguimgo.biblio.biblocentrale.service;

import com.bnguimgo.biblio.biblocentrale.dto.AuthorDTO;
import com.bnguimgo.biblio.biblocentrale.entity.Author;
import com.bnguimgo.biblio.biblocentrale.entity.Book;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioException;
import com.bnguimgo.biblio.biblocentrale.mapper.DtoMapper;
import com.bnguimgo.biblio.biblocentrale.repository.AuthorRepository;
import com.bnguimgo.biblio.biblocentrale.repository.BookRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

import static com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum.AUTHOR_CANNOT_DELETE;
import static com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum.AUTHOR_NOT_FOUND;

@Service
@Transactional(
        isolation = Isolation.READ_COMMITTED,
        propagation = Propagation.SUPPORTS,
        readOnly = true,
        timeout = 30)
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    @Autowired
    private DtoMapper mapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @ReadOnlyProperty
    public List<AuthorDTO> getAllAuthors() throws BiblioException {

        List<AuthorDTO> authors = authorRepository.findAll().stream()
                .map(mapper::mapToAuthorDTO).toList();
        if(authors.isEmpty()) {
            throw new BiblioException(AUTHOR_NOT_FOUND, HttpStatus.NOT_FOUND, AUTHOR_NOT_FOUND.getError());
        }
        return authors;
    }

    @ReadOnlyProperty
    public AuthorDTO getAuthorById(Long id) throws BiblioException {

        return authorRepository.findById(id).map(mapper::mapToAuthorDTO)
                .orElseThrow(() -> new BiblioException(BiblioErrorEnum.AUTHOR_NOT_FOUND, HttpStatus.NO_CONTENT, "No Author found with id " + id));
    }

    @ReadOnlyProperty
    public AuthorDTO findByFirstNameAndLastName(String firstName, String lastName) throws BiblioException {

        return authorRepository.findByFirstNameAndLastName(firstName, lastName).map(mapper::mapToAuthorDTO).orElseThrow(() -> new BiblioException(BiblioErrorEnum.AUTHOR_NOT_FOUND, HttpStatus.NO_CONTENT, "No Author found with firstName " + firstName +" and lastName "+lastName));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Modifying
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {

        authorDTO.getBooks().forEach(b -> b.setCreatedDate(LocalDateTime.now()));
        authorDTO.setCreatedDate(LocalDateTime.now());
        return mapper.mapToAuthorDTO(authorRepository.save(mapper.mapToAuthor(authorDTO)));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Modifying
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) throws BiblioException {

        return authorRepository.findById(id).map(author -> {

            author.setModifiedDate(LocalDateTime.now());
            author.setFirstName(authorDTO.getFirstName());
            author.setLastName(authorDTO.getLastName());

            //On valide la date de modification avec la méthode ci-dessous avant toute sauvegarde
            author = mapper.validateModifiedDate(author);

            return mapper.mapToAuthorDTO(authorRepository.save(author));

        }).orElseThrow(() -> new BiblioException(AUTHOR_NOT_FOUND, HttpStatus.NOT_FOUND, "Author not found with id: " + id));

    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Modifying
    public void deleteAuthor(Long authorId) throws BiblioException {

        Author author = authorRepository.findById(authorId).orElseThrow(() -> new BiblioException(AUTHOR_NOT_FOUND, HttpStatus.NOT_FOUND, "Author not found with id = " + authorId));
        Set<Book> books = author.getBooks();
        if(!books.isEmpty()) {
            if(books.stream().anyMatch(book -> ! book.getStudents().isEmpty())){ // On s'assure qu'aucun étudiant détient encore le livre
                throw new BiblioException(AUTHOR_CANNOT_DELETE, HttpStatus.BAD_REQUEST, "Cannot delete Author with existing borrowed books, please first delete or remove bookIds = "+
                        author.getBooks().stream().map(Book::getId).collect(Collectors.toSet()) + " from Author");
            } else {// Avant de supprimer un auteur, il faut supprimer ses livres
                Set<Long> booksIds = books.stream().map(Book::getId).collect(Collectors.toSet());
                bookRepository.deleteAllById(booksIds);
            }
        }
        authorRepository.deleteById(authorId);
    }
    
}
