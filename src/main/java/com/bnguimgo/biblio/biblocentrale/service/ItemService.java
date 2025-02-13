package com.bnguimgo.biblio.biblocentrale.service;

import com.bnguimgo.biblio.biblocentrale.entity.Item;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioException;
import com.bnguimgo.biblio.biblocentrale.repository.ItemRepository;
import com.bnguimgo.biblio.biblocentrale.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.bnguimgo.biblio.biblocentrale.exception.BiblioErrorEnum.*;

@Service
public class ItemService {
    
    private final ItemRepository itemRepository;
    
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createItem(Long studentId, Item item) throws BiblioException {

        return studentRepository.findById(studentId).map(student -> {

            Date now = Date.from(Instant.now());
            item.setCreatedDate(now);
            item.setModifiedDate(now);
            item.setStudent(student);

            return itemRepository.save(item);

        }).orElseThrow(() -> new BiblioException(STUDENT_NOT_FOUND, "Not found Student with id = " + studentId));
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item updateItem(Long itemId, Item item) throws BiblioException {

        return itemRepository.findById(itemId).map(itemFound -> {

            itemFound.setItemName(item.getItemName());
            itemFound.setItemCode(item.getItemCode());
            itemFound.setModifiedDate(Date.from(Instant.now()));//On met uniquement à jour la date de modification

            return itemRepository.save(item);

        }).orElseThrow(() -> new BiblioException(ITEM_NOT_FOUND, "Not found Item with id = " + itemId));
    }
    
    public Item updateItemStudent(Long itemId, Long studentId) throws BiblioException {

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new BiblioException(ITEM_NOT_FOUND, "Not found item with id = " + itemId));
        return studentRepository.findById(studentId).map(student -> {

            item.setModifiedDate(Date.from(Instant.now()));
            item.setStudent(student);

            return itemRepository.save(item);

        }).orElseThrow(() -> new BiblioException(AUTHOR_NOT_FOUND, "Not found Student with id = " + studentId));
    }
    
    public void deleteItem(Long id) throws BiblioException {

        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
        } else {
            throw new BiblioException(ITEM_NOT_FOUND, "Item not found with id: " + id);
        }
    }
}
