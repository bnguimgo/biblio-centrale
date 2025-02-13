package com.bnguimgo.biblio.biblocentrale.repository;

import com.bnguimgo.biblio.biblocentrale.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


}