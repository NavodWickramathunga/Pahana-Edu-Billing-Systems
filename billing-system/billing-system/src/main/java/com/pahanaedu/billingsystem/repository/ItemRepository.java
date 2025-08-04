// File: src/main/java/com/pahanaedu/billingsystem/repository/ItemRepository.java
package com.pahanaedu.billingsystem.repository;

import com.pahanaedu.billingsystem.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    // Basic CRUD operations are provided by MongoRepository.
}
