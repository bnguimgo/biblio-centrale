package com.bnguimgo.biblio.biblocentrale.mapper;

import com.bnguimgo.biblio.biblocentrale.dto.*;
import com.bnguimgo.biblio.biblocentrale.entity.Author;
import com.bnguimgo.biblio.biblocentrale.entity.Book;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DtoMapper {

    public BookDTO mapToBookDTO(Book book) {

        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .createdDate(book.getCreatedDate())
                .modifiedDate(book.getModifiedDate())
                .author(AuthorDtoProjection.builder()
                        .id(book.getAuthor().getId())
                        .firstName(book.getAuthor().getFirstName())
                        .lastName(book.getAuthor().getLastName())
                        .createdDate(book.getAuthor().getCreatedDate())
                        .modifiedDate(book.getAuthor().getModifiedDate())
                        .build())
                .build();
    }

    public BookDtoProjection mapToBookDTOFromAuthor(Book book) {

        return BookDtoProjection.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .createdDate(book.getCreatedDate())
                .modifiedDate(book.getModifiedDate())
                //.author(xxx) Pas besoin de Mapper l'auteur ici
                .build();
    }

    public Book mapToBook(BookDTO bookDTO) {

        return Book.builder()
                .id(bookDTO.getId())
                .title(bookDTO.getTitle())
                .isbn(bookDTO.getIsbn())
                .createdDate(bookDTO.getCreatedDate())
                .modifiedDate(bookDTO.getModifiedDate())
                //.author(mapToAuthor(bookDTO.getAuthor())) //pas besoin de mapper l'auteur, car l'auteur sera récupéré depuis avec son identifiant et associé à chaque livre
                .build();
    }

    public Author mapToAuthor(AuthorDTO authorDTO) {

        return Author.builder()
                .id(authorDTO.getId())
                .firstName(authorDTO.getFirstName())
                .lastName(authorDTO.getLastName())
                .gender(authorDTO.getGender())
                .emailAddress(authorDTO.getEmailAddress())
                .phoneNumber(authorDTO.getPhoneNumber())
                .age(authorDTO.getAge())
                .createdDate(authorDTO.getCreatedDate())
                .modifiedDate(authorDTO.getModifiedDate())
                .books(authorDTO.getBooks().stream()
                        .map(this::mapToBookFromBookDtoProjection)
                        .collect(Collectors.toSet()))
                .build();
    }

    private Book mapToBookFromBookDtoProjection(BookDtoProjection bookDtoProjection){

        return Book.builder()
                .id(bookDtoProjection.getId())
                .title(bookDtoProjection.getTitle())
                .isbn(bookDtoProjection.getIsbn())
                .modifiedDate(bookDtoProjection.getModifiedDate())
                .build();
    }

    private Set<Book> mapToBookFromBookDtoProjection(Set<BookDtoProjection> bookDtoProjection){

        return bookDtoProjection.stream().map(this::mapToBookFromBookDtoProjection).collect(Collectors.toSet());
    }

    public AuthorDTO mapToAuthorDTO(Author author) {

        return AuthorDTO.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .gender(author.getGender())
                .emailAddress(author.getEmailAddress())
                .phoneNumber(author.getPhoneNumber())
                .age(author.getAge())
                .createdDate(author.getCreatedDate())
                .modifiedDate(author.getModifiedDate())
                .books(author.getBooks().stream()
                        .map(this::mapToBookDTOFromAuthor)
                        .collect(Collectors.toSet()))
                .build();
    }

}
