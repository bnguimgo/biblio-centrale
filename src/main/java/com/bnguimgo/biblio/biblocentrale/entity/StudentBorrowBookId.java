package com.bnguimgo.biblio.biblocentrale.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

//@Embeddable
//Source: https://www.baeldung.com/jpa-many-to-many
//Creating a Composite Key in JPA
public class StudentBorrowBookId implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "book_id")
    private Long bookId;
}
