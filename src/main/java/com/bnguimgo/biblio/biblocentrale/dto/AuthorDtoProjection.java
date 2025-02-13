package com.bnguimgo.biblio.biblocentrale.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDtoProjection implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private Date createdDate;
    private Date modifiedDate;
}
