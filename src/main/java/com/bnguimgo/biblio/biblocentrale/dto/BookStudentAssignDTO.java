package com.bnguimgo.biblio.biblocentrale.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor //Permet de créer un contrôleur avec tous les paramètres
@NoArgsConstructor //Permet d'avoir un contrôleur par défaut
@EqualsAndHashCode
@Builder
//Source DTO: https://www.baeldung.com/java-dto-pattern
public class BookStudentAssignDTO implements Serializable {

    private Long id;

    private String title;
    private String isbn;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private StudentDtoProjection student;
}
