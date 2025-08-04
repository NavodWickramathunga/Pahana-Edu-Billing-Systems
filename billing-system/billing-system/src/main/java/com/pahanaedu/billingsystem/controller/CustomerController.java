// File: src/main/java/com/pahanaedu/billingsystem/controller/CustomerController.java
package com.pahanaedu.billingsystem.controller;

import com.pahanaedu.billingsystem.dto.CustomerDetailsDto;
import com.pahanaedu.billingsystem.model.Bill;
import com.pahanaedu.billingsystem.model.Customer;
import com.pahanaedu.billingsystem.service.BillService;
import com.pahanaedu.billingsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // New dependency for BillService to handle the profile logic here
    @Autowired
    private BillService billService;

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
            return new ResponseEntity<>("Login successful for " + authenticatedCustomer.getName(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid mobile number or password", HttpStatus.UNAUTHORIZED);
        }
    }

    // Endpoint to get customer details by account number
    @GetMapping("/{accountNumber}")
    public ResponseEntity<Customer> getCustomerDetails(@PathVariable String accountNumber) {
        Customer customer = customerService.getCustomerByAccountNumber(accountNumber);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to update an existing customer's information
    @PutMapping("/{accountNumber}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String accountNumber, @RequestBody Customer updatedCustomer) {
        Optional<Customer> customerOptional = customerService.updateCustomer(accountNumber, updatedCustomer);
        return customerOptional
                .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Refactored Endpoint to get a customer's profile and bills
    // Now directly handles the logic to get both customer and bill data
    @GetMapping("/profile/{accountNumber}")
    public ResponseEntity<CustomerDetailsDto> getCustomerProfile(@PathVariable String accountNumber) {
        Customer customer = customerService.getCustomerByAccountNumber(accountNumber);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // We now call the BillService directly from the controller
        List<Bill> bills = billService.getBillsByCustomerId(customer.getId());

        CustomerDetailsDto profile = new CustomerDetailsDto(customer, bills);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    // Inner class to represent the login request payload
    static class LoginRequest {
        private String mobileNumber;
        private String password;

        public String getMobileNumber() { return mobileNumber; }
        public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
