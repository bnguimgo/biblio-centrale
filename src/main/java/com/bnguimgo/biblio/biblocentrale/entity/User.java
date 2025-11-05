package com.bnguimgo.biblio.biblocentrale.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le prénom utilisateur est obligatoire")
    @Size(min = 2, max = 50)
    private String firstName;
    @NotBlank(message = "Le nom utilisateur est obligatoire")
    private String lastName;
    @Column(nullable = false, unique = true)
    @Size(min = 2, max = 50)
    @NotBlank(message = "L'email est obligatoire")
    private String email;
    private boolean actif;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
}
