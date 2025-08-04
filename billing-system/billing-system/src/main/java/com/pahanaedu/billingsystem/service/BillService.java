// File: src/main/java/com/pahanaedu/billingsystem/service/BillService.java
package com.pahanaedu.billingsystem.service;

import com.pahanaedu.billingsystem.model.Bill;
import com.pahanaedu.billingsystem.model.Customer;
import com.pahanaedu.billingsystem.model.Item;
import com.pahanaedu.billingsystem.repository.BillRepository;
import com.pahanaedu.billingsystem.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ItemRepository itemRepository;

    /**
     * Retrieves all bills from the database.
     * @return A list of all Bill objects.
     */
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    /**
     * Retrieves a single bill by its ID.
     * @param id The ID of the bill to retrieve.
     * @return An Optional containing the Bill if found, or empty otherwise.
     */
    public Optional<Bill> getBillById(String id) {
        return billRepository.findById(id);
    }

    /**
     * Retrieves all bills for a specific customer ID.
     * @param customerId The ID of the customer.
     * @return A list of bills for the given customer.
     */
    public List<Bill> getBillsByCustomerId(String customerId) {
        return billRepository.findByCustomerId(customerId);
    }

    /**
     * Creates a new bill.
     * @param bill The Bill object to save.
     * @return The saved Bill object.
     */
    public Bill createBill(Bill bill) {
        // You can add any business logic here before saving, e.g., validation.
        return billRepository.save(bill);
    }

    /**
     * Updates an existing bill.
     * @param id The ID of the bill to update.
     * @param updatedBill The Bill object with the new data.
     * @return An Optional containing the updated Bill, or empty if not found.
     */
    public Optional<Bill> updateBill(String id, Bill updatedBill) {
        Optional<Bill> existingBill = billRepository.findById(id);
        if (existingBill.isPresent()) {
            updatedBill.setId(id); // Ensure the ID is not changed
            return Optional.of(billRepository.save(updatedBill));
        }
        return Optional.empty();
    }

    /**
     * Deletes a bill by its ID.
     * @param id The ID of the bill to delete.
     */
    public void deleteBill(String id) {
        billRepository.deleteById(id);
    }
}
