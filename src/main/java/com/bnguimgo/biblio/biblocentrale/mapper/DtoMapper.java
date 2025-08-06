package com.bnguimgo.biblio.biblocentrale.mapper;

import com.bnguimgo.biblio.biblocentrale.dto.*;
import com.bnguimgo.biblio.biblocentrale.entity.Author;
import com.bnguimgo.biblio.biblocentrale.entity.Book;
import com.bnguimgo.biblio.biblocentrale.entity.Student;
import org.springframework.stereotype.Component;

import java.util.HashSet;
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

    public StudentBookDTO mapToBookDTOStudent(Book book) {

        return StudentBookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .createdDate(book.getCreatedDate())
                .modifiedDate(book.getModifiedDate())
                .students(book.getStudents().stream()
                        .map(this::mapToStudentDtoProjection)
                        .collect(Collectors.toSet()))
                .build();
    }

    public BookStudentAssignDTO mapToBookDTOStudent(Book book, Long studentId) {

        StudentDTO studentDTO = getStudent(book, studentId);
        return BookStudentAssignDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .createdDate(book.getCreatedDate())
                .modifiedDate(book.getModifiedDate())
                .student(StudentDtoProjection.builder()
                        .id(studentDTO.getId())
                        .lastName(studentDTO.getLastName())
                        .firstName(studentDTO.getFirstName())
                        .createdDate(studentDTO.getCreatedDate())
                        .modifiedDate(studentDTO.getModifiedDate())
                        .build())
                .build();
    }

    private StudentDTO getStudent(Book book, Long studentId) {
        return book.getStudents().stream()
                .filter(student -> student.getId().equals(studentId))
                .map(this::mapToStudentDTO)
                .findFirst().orElseThrow(null);
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
                .createdDate(bookDtoProjection.getCreatedDate())
                .modifiedDate(bookDtoProjection.getModifiedDate())
                .build();
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

    public StudentDTO mapToStudentDTO(Student student) {

        return StudentDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .createdDate(student.getCreatedDate())
                .modifiedDate(student.getModifiedDate())
                .books(student.getBooks().stream()
                        .map(this::mapToBookDTO)
                        .collect(Collectors.toSet()))
                .build();
    }

    public StudentDtoProjection mapToStudentDtoProjection(Student student) {

        return StudentDtoProjection.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .createdDate(student.getCreatedDate())
                .modifiedDate(student.getModifiedDate())
                .build();
    }

    public Student mapToStudent(StudentDTO studentDto) {

        return Student.builder()
                .id(studentDto.getId())
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .createdDate(studentDto.getCreatedDate())
                .modifiedDate(studentDto.getModifiedDate())
                .books(studentDto.getBooks().stream()
                        .map(this::mapToBook)
                        .collect(Collectors.toSet()))
                .build();
    }
}
