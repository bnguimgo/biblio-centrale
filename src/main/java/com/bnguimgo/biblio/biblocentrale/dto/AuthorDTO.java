package com.bnguimgo.biblio.biblocentrale.dto;

import com.bnguimgo.biblio.biblocentrale.entity.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode
@ToString(exclude = {"books"})
@Builder
public class AuthorDTO implements Serializable {

    private Long id;

    private String firstName;
    private String lastName;
    private String gender;
    private String emailAddress;
    private String phoneNumber;
    private int age;
    private Date createdDate;
    private Date modifiedDate;
    //private Set<BookDTO> books = new HashSet<>();
    private Set<BookDtoProjection> books = new HashSet<>();
/*    public void addBook(BookDTO book) {
        books.add(book);
        book.setAuthor(this);
    }*/

    public void removeBook(BookDTO book) {
        books.remove(book);
        book.setAuthor(null);
    }
}
