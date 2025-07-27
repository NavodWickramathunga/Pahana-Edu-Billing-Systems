// Save this file as: src/main/java/com/pahanaedu/billingsystem/repository/CustomerRepository.java
package com.pahanaedu.billingsystem.repository; // This is your folder structure

import com.pahanaedu.billingsystem.model.Customer; // Import your Customer form blueprint
import org.springframework.data.mongodb.repository.MongoRepository; // This is the special Spring MongoDB helper
import org.springframework.stereotype.Repository; // Tells Spring this is a data handler

@Repository // Marks this as a Spring component for handling data
public interface CustomerRepository extends MongoRepository<Customer, String> {
    // This line is magic! By extending MongoRepository, Spring automatically gives you:
    // - save() to create/update a customer
    // - findById() to get a customer by their unique ID
    // - findAll() to get all customers
    // - deleteById() to remove a customer by their unique ID
    // ... and many more basic operations!

    // You can also add custom finding methods here. For example, to find by mobile number:
    Customer findByMobileNumber(String mobileNumber);
}