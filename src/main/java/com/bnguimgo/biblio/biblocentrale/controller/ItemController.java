package com.bnguimgo.biblio.biblocentrale.controller;

import com.bnguimgo.biblio.biblocentrale.entity.Item;
import com.bnguimgo.biblio.biblocentrale.exception.BiblioException;
import com.bnguimgo.biblio.biblocentrale.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/students/{studentId}")
    public ResponseEntity<Item> createItem(@PathVariable(value = "studentId") Long studentId,
                                           @RequestBody Item item) throws BiblioException {

        return new ResponseEntity<>(itemService.createItem(studentId, item), HttpStatus.CREATED);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable(value = "itemId") Long itemId) {
        return itemService.getItemById(itemId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Item> updateItem(@PathVariable(value = "itemId") Long itemId,
                                              @RequestBody Item item) throws BiblioException {

        return new ResponseEntity<>(itemService.updateItem(itemId, item), HttpStatus.CREATED);
    }

    @PutMapping("/{itemId}/students/{studentId}")
    public ResponseEntity<Item> updateItemAndStudent(@PathVariable(value = "itemId") Long itemId,
                                              @PathVariable(value = "studentId") Long studentId) throws BiblioException {

        return new ResponseEntity<>(itemService.updateItemStudent(itemId, studentId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable(value = "itemId") Long itemId) throws BiblioException {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

}