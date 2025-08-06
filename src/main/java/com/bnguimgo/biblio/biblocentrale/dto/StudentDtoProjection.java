package com.bnguimgo.biblio.biblocentrale.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor //Permet de créer un contrôleur avec tous les paramètres
@NoArgsConstructor //Permet d'avoir un contrôleur par défaut
@ToString          // Surcharge la méthode toString de l'objet en cours
@Builder
public class StudentDtoProjection {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}
