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
     * Calculates and creates a new bill based on customer units consumed and item unit price.
     * @param customerId The ID of the customer.
     * @param unitsConsumed The number of units to bill for.
     * @param itemId The ID of the billing item (e.g., "electricity_item_id").
     * @return The newly created Bill object.
     */
    public Bill calculateAndCreateBill(String customerId, int unitsConsumed, String itemId) {
        // 1. Validate customer existence
        Optional<Customer> customerOptional = customerService.getCustomerById(customerId);
        if (customerOptional.isEmpty()) {
            throw new RuntimeException("Customer with ID " + customerId + " not found.");
        }

        // 2. Fetch the item to get the unit price
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isEmpty()) {
            throw new RuntimeException("Item with ID " + itemId + " not found. Cannot calculate bill.");
        }
        double unitPrice = itemOptional.get().getUnitPrice();

        // 3. Calculate the total bill amount
        double totalAmount = unitsConsumed * unitPrice;

        // 4. Create the new bill document
        Bill newBill = new Bill();
        newBill.setCustomerId(customerId);
        newBill.setUnitsConsumed(unitsConsumed);
        newBill.setAmount(totalAmount);
        newBill.setBillDate(LocalDate.now());
        newBill.setDueDate(LocalDate.now().plusDays(30)); // Due in 30 days
        newBill.setStatus("UNPAID");

        return billRepository.save(newBill);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Optional<Bill> getBillById(String id) {
        return billRepository.findById(id);
    }

    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }

    public List<Bill> getBillsByCustomerId(String customerId) {
        return billRepository.findByCustomerId(customerId);
    }

    /**
     * Placeholder method to satisfy other services (e.g., PaymentService) that
     * may need to update a bill. We will implement the full logic later.
     * @param billId The ID of the bill to update.
     * @param updatedBill The bill object with updated details.
     * @return The updated Bill object.
     */
    public Bill updateBill(String billId, Bill updatedBill) {
        // Here we can simply find the bill and save the updated version.
        Optional<Bill> existingBill = billRepository.findById(billId);
        if (existingBill.isPresent()) {
            updatedBill.setId(billId); // Ensure the ID is not changed
            return billRepository.save(updatedBill);
        }
        throw new RuntimeException("Bill with ID " + billId + " not found.");
    }
}
