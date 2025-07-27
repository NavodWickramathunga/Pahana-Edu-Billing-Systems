// File: src/main/java/com/pahanaedu/billingsystem/repository/ProductRepository.java
package com.pahanaedu.billingsystem.repository;

import com.pahanaedu.billingsystem.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Marks this as a Spring component for data access
public interface ProductRepository extends MongoRepository<Product, String> {
    // MongoRepository provides basic CRUD operations for Product documents.

    // Custom query method to find a product by its name
    Optional<Product> findByName(String name);
}
