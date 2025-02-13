package com.bnguimgo.biblio.biblocentrale.repository;

import com.bnguimgo.biblio.biblocentrale.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}