package com.bnguimgo.biblio.biblocentrale.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor //Permet de créer un contrôleur avec tous les paramètres
@NoArgsConstructor //Permet d'avoir un contrôleur par défaut
//@EqualsAndHashCode
@ToString//(exclude = {"items"})           // Surcharge la méthode toString de l'objet en cours
@Builder
@Entity
@Table(name = "students")
//Source: https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le prénom de l'auteur est obligatoire")
    private String firstName;
    @NotBlank(message = "Le nom de l'auteur est obligatoire")
    private String lastName;

    @Column(updatable = false)
    private Date createdDate;
    private Date modifiedDate;

    @JsonManagedReference
    //@OneToMany(mappedBy="student", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy="student", cascade = CascadeType.ALL)
    public Set<Item> items;


    //Source: https://www.baeldung.com/jpa-many-to-many
    //Note that using @JoinTable or even @JoinColumn isn’t required. JPA will generate the table and column names for us.
    // However, the strategy JPA uses won’t always match the naming conventions we use. So, we need the possibility
    // to configure table and column names.
    @ManyToMany
    @JoinTable(
            name = "borrow_book",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    Set<Book> books = new HashSet<>();

}
