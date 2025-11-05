package com.bnguimgo.biblio.biblocentrale.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor //Permet de créer un contrôleur avec tous les paramètres
@NoArgsConstructor //Permet d'avoir un contrôleur par défaut
//@EqualsAndHashCode
@ToString(exclude = {"books"})           // Surcharge la méthode toString de l'objet en cours
@Builder
@Entity
@Table(name = "students")
//Source: https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Evitez d'utiliser GenerationType.AUTO qui est la génération de la clé primaire par l'ORM, car cela peut créer des inconsistances
    //Source: https://vladmihalcea.com/why-should-not-use-the-auto-jpa-generationtype-with-mysql-and-hibernate/
    private Long id;

    @NotBlank(message = "Le prénom de l'auteur est obligatoire")
    private String firstName;
    @NotBlank(message = "Le nom de l'auteur est obligatoire")
    private String lastName;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    //Source: https://www.baeldung.com/jpa-many-to-many
    //Note that using @JoinTable or even @JoinColumn isn’t required. JPA will generate the table and column names for us.
    // However, the strategy JPA uses won’t always match the naming conventions we use. So, we need the possibility
    // to configure table and column names.
    @ManyToMany
    @JoinTable(
            name = "borrow_book",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    Set<Book> books;

}
