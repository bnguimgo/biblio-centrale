package com.bnguimgo.biblio.biblocentrale.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Entity
@Table(name = "utilisateurs")
public class Manager implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le prénom utilisateur est obligatoire")
    private String firstName;
    @NotBlank(message = "Le nom utilisateur est obligatoire")
    private String lastName;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
