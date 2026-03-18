package com.bnguimgo.biblio.biblocentrale.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    @NotBlank(message = "Le titre est obligatoire")
    private String title;
    private String isbn;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private AuthorDtoProjection author;
}
