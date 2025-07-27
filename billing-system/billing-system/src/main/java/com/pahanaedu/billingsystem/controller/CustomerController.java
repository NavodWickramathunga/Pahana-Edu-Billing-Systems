// File: src/main/java/com/pahanaedu/billingsystem/controller/CustomerController.java
package com.pahanaedu.billingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // Make sure this import is present!
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pahanaedu.billingsystem.model.Customer;
import com.pahanaedu.billingsystem.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // 1. To get ALL customers: GET http://localhost:8080/api/customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // 2. To get a customer by ID: GET http://localhost:8080/api/customers/some_id_here
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id)
                .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Corrected from NOT_NOT_FOUND
    }

    // 3. To CREATE a new customer (Registration): POST http://localhost:8080/api/customers
    //    Body (JSON): { "name": "John Doe", "address": "123 Main St", "mobileNumber": "0712345678", "password": "securepassword" }
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            Customer newCustomer = customerService.createCustomer(customer);
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // If mobile number already exists, return a 400 Bad Request
            // In a real app, you might want a more specific error message from the service
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 4. To UPDATE an existing customer: PUT http://localhost:8080/api/customers/some_id_here
    //    Body (JSON): { "name": "John Doe Updated", "address": "456 New St" }
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(id, customer);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. To DELETE a customer: DELETE http://localhost:8080/api/customers/some_id_here
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable String id) {
        try {
            customerService.deleteCustomer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 6. To LOGIN a customer: POST http://localhost:8080/api/customers/login
    //    Body (JSON): { "mobileNumber": "0712345678", "password": "securepassword" }
    @PostMapping("/login")
    public ResponseEntity<String> loginCustomer(@RequestBody Customer loginRequest) {
        // Call the service to authenticate
        boolean isAuthenticated = customerService.authenticateCustomer(
                loginRequest.getMobileNumber(),
                loginRequest.getPassword()
        );

        if (isAuthenticated) {
            // In a real app, you'd generate a token (JWT) here, not just return "Login successful"
            return new ResponseEntity<>("Login successful!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid mobile number or password", HttpStatus.UNAUTHORIZED); // 401 Unauthorized
        }
    }
}