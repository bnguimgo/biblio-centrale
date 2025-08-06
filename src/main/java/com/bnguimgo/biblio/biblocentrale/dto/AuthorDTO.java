package com.bnguimgo.biblio.biblocentrale.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
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

    private String firstName;
    private String lastName;
    private String gender;
    private String emailAddress;
    private String phoneNumber;
    private int age;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Set<BookDtoProjection> books = new HashSet<>();
}
