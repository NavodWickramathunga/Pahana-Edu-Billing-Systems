// Save this file as: src/main/java/com/pahanaedu/billingsystem/service/CustomerService.java
package com.pahanaedu.billingsystem.service;

import com.pahanaedu.billingsystem.model.Customer;
import com.pahanaedu.billingsystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        if (customerRepository.findByMobileNumber(customer.getMobileNumber()) != null) {
            throw new RuntimeException("Mobile number already registered!");
        }

        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);

        return customerRepository.save(customer);
    }

    public Customer updateCustomer(String id, Customer customerDetails) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));

        existingCustomer.setName(customerDetails.getName());
        existingCustomer.setAddress(customerDetails.getAddress());
        existingCustomer.setTelephoneNumber(customerDetails.getTelephoneNumber());
        existingCustomer.setUnitsConsumed(customerDetails.getUnitsConsumed());
        // Do NOT update mobileNumber or password directly here, as they are sensitive
        // You would have separate methods for updating those securely.

        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    // This method is correctly defined here and does not override a supertype method
    public Customer findByMobileNumber(String mobileNumber) {
        return customerRepository.findByMobileNumber(mobileNumber);
    }

    public boolean authenticateCustomer(String mobileNumber, String rawPassword) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber);
        if (customer != null) {
            return passwordEncoder.matches(rawPassword, customer.getPassword());
        }
        return false;
    }
}