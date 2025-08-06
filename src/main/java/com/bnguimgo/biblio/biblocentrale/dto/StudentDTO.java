package com.bnguimgo.biblio.biblocentrale.dto;

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
@ToString          // Surcharge la méthode toString de l'objet en cours
@Builder
public class StudentDTO {

    private Long id;

    @NotBlank(message = "Le prénom de l'auteur est obligatoire")
    private String firstName;
    @NotBlank(message = "Le nom de l'auteur est obligatoire")
    private String lastName;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    Set<BookDTO> books = new HashSet<>();

}
