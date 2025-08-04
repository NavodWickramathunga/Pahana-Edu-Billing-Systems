// File: src/main/java/com/pahanaedu/billingsystem/controller/ItemController.java
package com.pahanaedu.billingsystem.controller;

import com.pahanaedu.billingsystem.model.Item;
import com.pahanaedu.billingsystem.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/items") // Admin-specific endpoint
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    // Admin endpoint to get all items
    @GetMapping
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Admin endpoint to add a new item
    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        Item newItem = itemRepository.save(item);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    // Admin endpoint to update an existing item
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable String id, @RequestBody Item updatedItem) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isPresent()) {
            Item existingItem = itemOptional.get();
            existingItem.setName(updatedItem.getName());
            existingItem.setUnitPrice(updatedItem.getUnitPrice());
            existingItem.setDescription(updatedItem.getDescription());
            Item savedItem = itemRepository.save(existingItem);
            return new ResponseEntity<>(savedItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Admin endpoint to delete an item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable String id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
