// File: src/main/java/com/pahanaedu/billingsystem/repository/CustomerRepository.java
package com.pahanaedu.billingsystem.repository;

import com.pahanaedu.billingsystem.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    // Custom query method to find a customer by their mobile number
    Optional<Customer> findByMobileNumber(String mobileNumber);
}