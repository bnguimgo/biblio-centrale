package com.bnguimgo.biblio.biblocentrale.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

//import javax.persistence.Entity;// On utilise plus le package javax

//@Data   // ATTENTION: BUG Nécessaire pour créer implicitement les Getters et Setters
@Getter
@Setter
@AllArgsConstructor //Permet de créer un contrôleur avec tous les paramètres
@NoArgsConstructor //Permet d'avoir un contrôleur par défaut
@ToString(exclude = {"students"})           // Surcharge la méthode toString de l'objet en cours
@EqualsAndHashCode
@Builder
//Source DTO: https://www.baeldung.com/java-dto-pattern
public class StudentBookDTO implements Serializable {

    private Long id;

    private String title;
    private String isbn;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    Set<StudentDtoProjection> students = new HashSet<>();
}
