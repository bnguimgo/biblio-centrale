package com.bnguimgo.biblio.biblocentrale.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode
@ToString(exclude = {"books"})
@Builder
public class AuthorDTO implements Serializable {

    private Long id;

    @NotBlank(message = "Le prénom est obligatoire")
    private String firstName;
    @NotBlank(message = "Le nom est obligatoire")
    private String lastName;
    private String gender;
    private String emailAddress;
    private String phoneNumber;
    private int age;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Set<BookDtoProjection> books;
}
