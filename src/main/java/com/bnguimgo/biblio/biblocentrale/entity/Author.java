package com.bnguimgo.biblio.biblocentrale.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    //@Length(min = 10, max = 10, message = "Le numéro de téléphone doit être sur 10 chiffres")
    //L'annotation @Size est préférable à @Length (spécifique à Hibernate), car @Size rend le bean indépendant d'un Framework externe,
    //et par conséquent, le bean est plus portable
    //@Length et @Size permettent toutes deux, de valider un bean, alors que  @Column(length = 10) génère une validation SQL d'insertion
    @Size(min = 10, max = 10, message = "Le numéro de téléphone doit être sur 10 chiffres")
    @Column(length = 10)//Ceci va générer dans la base de données une colonne avec VARCHAR(10), et l'insertion d'une valeur supérieure à 10 caractères va générer une erreur
    private String phoneNumber;
    private int age;
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    //@OneToMany(mappedBy="author", cascade = CascadeType.ALL, orphanRemoval = true)
    //Source IMPORTANTE: https://stackoverflow.com/questions/43357413/parent-id-null-in-onetomany-mapping-jpa
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "AUTHOR_ID")
    private Set<Book> books;

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
