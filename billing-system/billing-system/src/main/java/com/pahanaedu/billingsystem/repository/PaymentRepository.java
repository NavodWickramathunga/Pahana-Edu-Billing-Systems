// File: src/main/java/com/pahanaedu/billingsystem/repository/PaymentRepository.java
package com.pahanaedu.billingsystem.repository;

import com.pahanaedu.billingsystem.model.Payment; // Import your Payment model
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Marks this as a Spring component for data access
public interface PaymentRepository extends MongoRepository<Payment, String> {
    // MongoRepository provides basic CRUD operations for Payment documents.

    // Custom query method to find payments by customer ID
    List<Payment> findByCustomerId(String customerId);

    // Custom query method to find payments by bill ID
    List<Payment> findByBillId(String billId);

    // You could add more custom queries here if needed, e.g.,
    // List<Payment> findByPaymentMethod(String paymentMethod);
    // List<Payment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);
}
