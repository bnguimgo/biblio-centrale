package com.bnguimgo.biblio.biblocentrale.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Le prénom utilisateur est obligatoire")
    @Size(min = 2, max = 50)
    private String firstName;
    @NotBlank(message = "Le nom utilisateur est obligatoire")
    private String lastName;
    @Size(min = 2, max = 50)
    @NotBlank(message = "L'email est obligatoire")
    private String email;
    private boolean actif;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    private Set<String> roles;
}
