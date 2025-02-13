package com.bnguimgo.biblio.biblocentrale.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor //Permet de créer un contrôleur avec tous les paramètres
@NoArgsConstructor //Permet d'avoir un contrôleur par défaut
@ToString(exclude = {"student"})           // Surcharge la méthode toString de l'objet en cours
@EqualsAndHashCode ///////// ATTENTION: Ne pas activer, sinon cela crée une bouche infinie avec l'erreur: java.lang.StackOverflowError
@Builder
@Entity
@Table(name = "items") // Optionnel: Permet de donner un nom explicite à la table
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre du livre est obligatoire")
    private String itemName;
    @NotBlank(message = "Le titre du livre est obligatoire")
    private String itemCode;

    @Column(updatable = false)
    private Date createdDate;
    private Date modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    //@ToString.Exclude
    @JoinColumn(name="student_id", nullable=false)//keep or remove this for bidirectionnal ?
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    @JsonBackReference
    //Cette annotation est nécessaire, afin d'éviter une boucle infinie lors de la sérialization pendant l'extraction de l'auteur du livre (voie la méthode getAllAuthors())
    private Student student;

}
