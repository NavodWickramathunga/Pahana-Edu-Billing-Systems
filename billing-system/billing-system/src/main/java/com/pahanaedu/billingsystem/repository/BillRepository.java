// File: src/main/java/com/pahanaedu/billingsystem/repository/BillRepository.java
package com.pahanaedu.billingsystem.repository;

import com.pahanaedu.billingsystem.model.Bill; // Import your Bill model
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Marks this as a Spring component for data access
public interface BillRepository extends MongoRepository<Bill, String> {
    // MongoRepository provides basic CRUD operations for Bill documents.

    // Custom query method to find bills by customer ID
    List<Bill> findByCustomerId(String customerId);

    // Custom query method to find bills by status
    List<Bill> findByStatus(String status);
}