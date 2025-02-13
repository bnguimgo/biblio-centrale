package com.bnguimgo.biblio.biblocentrale.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

//import javax.persistence.Entity;// On utilise plus le package javax

//@Data   // ATTENTION: BUG Nécessaire pour créer implicitement les Getters et Setters
@Getter
@Setter
@AllArgsConstructor //Permet de créer un contrôleur avec tous les paramètres
@NoArgsConstructor //Permet d'avoir un contrôleur par défaut
@ToString(exclude = {"author"})           // Surcharge la méthode toString de l'objet en cours
@EqualsAndHashCode
@Builder
//Source DTO: https://www.baeldung.com/java-dto-pattern
public class BookDTO implements Serializable {

    private Long id;

    private String title;
    private String isbn;
    private Date createdDate;
    private Date modifiedDate;
    //private AuthorDTO author;
    private AuthorDtoProjection author;
}
