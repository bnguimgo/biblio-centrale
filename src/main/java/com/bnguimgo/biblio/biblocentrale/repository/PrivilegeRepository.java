package com.bnguimgo.biblio.biblocentrale.repository;

import com.bnguimgo.biblio.biblocentrale.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {


    Privilege findByName(String name);
}
