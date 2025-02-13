package com.bnguimgo.biblio.biblocentrale.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@ToString//(exclude = {"books"})
@Builder
@Entity
@Table(name = "authors")
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le prénom de l'auteur est obligatoire")
    private String firstName;
    @NotBlank(message = "Le nom de l'auteur est obligatoire")
    private String lastName;
    private String gender;
    @Email(message = "L'email n'est pas valide")
    @NotBlank(message = "L'email est requis")
    private String emailAddress;
    @Length(min = 10, max = 10, message = "Le numéro de téléphone doit être sur 10 chiffres")
    private String phoneNumber;
    private int age;
    //@DateTimeFormat(pattern="dd-MM-yyyy")
    @Column(updatable = false)
    private Date createdDate;
    //@DateTimeFormat(pattern="dd-MM-yyyy")
    private Date modifiedDate;
    @OneToMany(mappedBy="author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Book> books = new HashSet<>();

    // Source à exploiter: https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
    //The parent entity, Post, features two utility methods (e.g. addComment and removeComment)
    // which are used to synchronize both sides of the bidirectional association. You should always
    // provide these methods whenever you are working with a bidirectional association as, otherwise,
    // you risk very subtle state propagation issues.
    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
    }


}
