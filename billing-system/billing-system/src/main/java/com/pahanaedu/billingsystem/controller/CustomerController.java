// File: src/main/java/com/pahanaedu/billingsystem/controller/CustomerController.java
package com.pahanaedu.billingsystem.controller;

import com.pahanaedu.billingsystem.model.Customer;
import com.pahanaedu.billingsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController // This tells Spring this class handles REST API requests
@RequestMapping("/api/customers") // All methods in this class will respond to URLs starting with /api/customers
public class CustomerController {

    @Autowired // Spring will automatically provide an instance of CustomerService here
    private CustomerService customerService;

    // Endpoint for new customer registration
    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody Customer customer) {
        try {
            customerService.registerNewCustomer(customer);
            return new ResponseEntity<>("Customer registered successfully!", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        Customer authenticatedCustomer = customerService.authenticateUser(loginRequest.getMobileNumber(), loginRequest.getPassword());
        if (authenticatedCustomer != null) {
            // In a real application, you'd return a JWT token here
            return new ResponseEntity<>("Login successful for " + authenticatedCustomer.getName(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid mobile number or password", HttpStatus.UNAUTHORIZED);
        }
    }

    // Endpoint to get customer details by account number (example for authenticated users)
    @GetMapping("/{accountNumber}")
    public ResponseEntity<Customer> getCustomerDetails(@PathVariable String accountNumber) {
        Customer customer = customerService.getCustomerByAccountNumber(accountNumber);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // New Endpoint to update an existing customer's information
    // Handles: PUT http://localhost:8080/api/customers/{accountNumber}
    @PutMapping("/{accountNumber}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String accountNumber, @RequestBody Customer updatedCustomer) {
        // Use Optional to handle the case where the customer might not exist
        Optional<Customer> customerOptional = customerService.updateCustomer(accountNumber, updatedCustomer);
        return customerOptional
                .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK)) // If found, return the updated customer
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // If not found, return 404
    }

    // Inner class to represent the login request payload
    static class LoginRequest {
        private String mobileNumber;
        private String password;

        // Getters and Setters
        public String getMobileNumber() { return mobileNumber; }
        public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
