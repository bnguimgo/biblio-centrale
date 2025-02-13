package com.bnguimgo.biblio.biblocentrale.repository;

import com.bnguimgo.biblio.biblocentrale.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    //Set<Item> findAllByIdIn(List<Long> ids);
    Set<Item> findAllByIdIn(Set<Long> ids);
}