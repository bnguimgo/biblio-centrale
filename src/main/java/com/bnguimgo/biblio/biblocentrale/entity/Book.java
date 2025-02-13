package com.bnguimgo.biblio.biblocentrale.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//import javax.persistence.Entity;// On utilise plus le package javax

//@Data   // ATTENTION: BUG Nécessaire pour créer implicitement les Getters et Setters
@Getter
@Setter
@AllArgsConstructor //Permet de créer un contrôleur avec tous les paramètres
@NoArgsConstructor //Permet d'avoir un contrôleur par défaut
@ToString//(exclude = {"author"})           // Surcharge la méthode toString de l'objet en cours
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
    //@DateTimeFormat(pattern="dd-MM-yyyy")
    @Column(updatable = false)
    private Date createdDate;
    //@DateTimeFormat(pattern="dd-MM-yyyy")
    private Date modifiedDate;

    // Explication: The @ManyToOne association uses FetchType.LAZY because,
    // otherwise, we’d fall back to EAGER fetching which is bad for performance.
    //https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    //@ToString.Exclude
    @JoinColumn(name="author_id", nullable=false)//keep or remove this for bidirectionnal ?
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore  //Cette annotation est nécessaire, afin d'éviter une boucle infinie lors de la sérialization pendant l'extraction de l'auteur du livre (voie la méthode getAllAuthors())
    private Author author;


    @ManyToMany(mappedBy = "books")
    Set<Student> students = new HashSet<>();
}
