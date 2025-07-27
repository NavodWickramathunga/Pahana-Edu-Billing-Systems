// File: src/main/java/com/pahanaedu/billingsystem/service/BillService.java
package com.pahanaedu.billingsystem.service;

import com.pahanaedu.billingsystem.model.Bill;
import com.pahanaedu.billingsystem.model.Customer; // To check customer existence
import com.pahanaedu.billingsystem.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service // Marks this as a Spring service component
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private CustomerService customerService; // To validate customer IDs

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Optional<Bill> getBillById(String id) {
        return billRepository.findById(id);
    }

    public Bill createBill(Bill bill) {
        // Business logic: Ensure the customer exists before creating a bill for them
        Optional<Customer> customer = customerService.getCustomerById(bill.getCustomerId());
        if (customer.isEmpty()) {
            throw new RuntimeException("Customer with ID " + bill.getCustomerId() + " not found. Cannot create bill.");
        }

        // Set default status if not provided
        if (bill.getStatus() == null || bill.getStatus().isEmpty()) {
            bill.setStatus("UNPAID");
        }
        // Set billDate to today if not provided
        if (bill.getBillDate() == null) {
            bill.setBillDate(LocalDate.now());
        }
        // Set dueDate (e.g., 30 days from billDate) if not provided
        if (bill.getDueDate() == null) {
            bill.setDueDate(bill.getBillDate().plusDays(30));
        }

        return billRepository.save(bill);
    }

    public Bill updateBill(String id, Bill billDetails) {
        Bill existingBill = billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found with id " + id));

        // Update fields that are allowed to be changed
        existingBill.setBillDate(billDetails.getBillDate());
        existingBill.setDueDate(billDetails.getDueDate());
        existingBill.setAmount(billDetails.getAmount());
        existingBill.setStatus(billDetails.getStatus());
        existingBill.setUnitsConsumed(billDetails.getUnitsConsumed());
        existingBill.setMeterReading(billDetails.getMeterReading());
        // Do NOT allow changing customerId directly in an update, typically.
        // If a bill needs to be re-assigned, it's usually a new bill or a specific business process.

        return billRepository.save(existingBill);
    }

    public void deleteBill(String id) {
        billRepository.deleteById(id);
    }

    public List<Bill> getBillsByCustomerId(String customerId) {
        return billRepository.findByCustomerId(customerId);
    }

    public List<Bill> getBillsByStatus(String status) {
        return billRepository.findByStatus(status);
    }
}
