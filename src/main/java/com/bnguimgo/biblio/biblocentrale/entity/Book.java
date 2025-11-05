package com.bnguimgo.biblio.biblocentrale.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

//@Data   // ATTENTION: BUG Nécessaire pour créer implicitement les Getters et Setters
@Getter
@Setter
@AllArgsConstructor //Permet de créer un contrôleur avec tous les paramètres
@NoArgsConstructor //Permet d'avoir un contrôleur par défaut
@ToString(exclude = {"author"})           // Surcharge la méthode toString de l'objet en cours
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "books") // Optionnel: Permet de donner un nom explicite à la table
public class Book  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre du livre est obligatoire")
    private String title;
    @NotBlank(message = "Le numéro du livre est obligatoire")
    private String isbn; //(ISBN: International Standard Book Number = Numéro unique d'identification du livre sur le plan international)
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    // Explication: The @ManyToOne association uses FetchType.LAZY because,
    // otherwise, we’d fall back to EAGER fetching which is bad for performance.
    //https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
    @ManyToOne(fetch = FetchType.LAZY)
    //@ToString.Exclude
    //@JoinColumn(name="author_id", nullable=false)//keep or remove this for bidirectionnal ?
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore  //Cette annotation est nécessaire, afin d'éviter une boucle infinie lors de la sérialization pendant l'extraction de l'auteur du livre (voie la méthode getAllAuthors())
    private Author author;


    @ManyToMany(mappedBy = "books")
    @JsonIgnore
    Set<Student> students;

    public void addStudent(Student student) {
        students.add(student);
        student.getBooks().add(this);
    }

    //Source https://www.baeldung.com/jpa-remove-entity-many-to-many
    public void removeStudent(Student student) {
        //NB : Ne pas permuter l'ordre ci-dessous, sinon, ça ne marchera pas, il n'y aura pas de retrait du livre pour l'étudiant
        //de plus, ne pas mettre à null exemple : student.setBooks(null), si vous faites ça, la liste de tous les livres empruntés
        //par cet étudiant est réinitialisée à null, pourtant il peut y avoir d'autres emprunts d'un auteur différent
        student.getBooks().remove(this);
        students.remove(student);

    }
}
