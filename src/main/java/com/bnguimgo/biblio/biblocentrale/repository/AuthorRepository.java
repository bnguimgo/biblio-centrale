package com.bnguimgo.biblio.biblocentrale.repository;

import com.bnguimgo.biblio.biblocentrale.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {


}