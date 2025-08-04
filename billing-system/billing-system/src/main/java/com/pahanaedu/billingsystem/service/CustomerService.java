// File: src/main/java/com/pahanaedu/billingsystem/service/CustomerService.java
package com.pahanaedu.billingsystem.service;

import com.pahanaedu.billingsystem.model.Customer;
import com.pahanaedu.billingsystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new customer after hashing the password.
     * Throws an exception if a customer with the same mobile number already exists.
     */
    public void registerNewCustomer(Customer customer) {
        if (customerRepository.findByMobileNumber(customer.getMobileNumber()).isPresent()) {
            throw new RuntimeException("A customer with this mobile number already exists.");
        }
        // Hash the password before saving for security
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
    }

    /**
     * Authenticates a user based on mobile number and password.
     *
     * @param mobileNumber The user's mobile number.
     * @param password     The user's raw password.
     * @return The authenticated Customer object or null if authentication fails.
     */
    public Customer authenticateUser(String mobileNumber, String password) {
        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(mobileNumber);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            // Check if the provided password matches the stored, hashed password
            if (passwordEncoder.matches(password, customer.getPassword())) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Retrieves a customer by their account number.
     * @param accountNumber The unique account number of the customer.
     * @return The Customer object or null if not found.
     */
    public Customer getCustomerByAccountNumber(String accountNumber) {
        return customerRepository.findById(accountNumber).orElse(null);
    }

    /**
     * Retrieves a customer by their ID. This is a helper method for other services.
     * @param id The MongoDB _id of the customer.
     * @return An Optional containing the Customer if found.
     */
    public Optional<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    /**
     * Updates a customer's details by their account number.
     *
     * @param accountNumber   The unique account number of the customer.
     * @param updatedCustomer The customer object with updated details.
     * @return An Optional containing the updated Customer, or empty if not found.
     */
    public Optional<Customer> updateCustomer(String accountNumber, Customer updatedCustomer) {
        Optional<Customer> existingCustomerOptional = customerRepository.findById(accountNumber);
        if (existingCustomerOptional.isPresent()) {
            Customer existingCustomer = existingCustomerOptional.get();
            // Update fields that are allowed to be changed
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setAddress(updatedCustomer.getAddress());
            existingCustomer.setTelephoneNumber(updatedCustomer.getTelephoneNumber());
            existingCustomer.setMobileNumber(updatedCustomer.getMobileNumber()); // Update mobile number as well
            existingCustomer.setUnitsConsumed(updatedCustomer.getUnitsConsumed());
            // For security, do not update the password here directly.
            // A separate endpoint for password change is a better practice.
            return Optional.of(customerRepository.save(existingCustomer));
        }
        return Optional.empty(); // Return empty if customer is not found
    }
}
