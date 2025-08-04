// File: src/main/java/com/pahanaedu/billingsystem/controller/BillController.java
package com.pahanaedu.billingsystem.controller;

import com.pahanaedu.billingsystem.model.Bill;
import com.pahanaedu.billingsystem.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    /**
     * Retrieves a list of all bills.
     * Handles: GET http://localhost:8080/api/bills
     */
    @GetMapping
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    /**
     * Retrieves a single bill by its ID.
     * Handles: GET http://localhost:8080/api/bills/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable String id) {
        Optional<Bill> bill = billService.getBillById(id);
        return bill.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all bills for a specific customer ID.
     * Handles: GET http://localhost:8080/api/bills/customer/{customerId}
     */
    @GetMapping("/customer/{customerId}")
    public List<Bill> getBillsByCustomerId(@PathVariable String customerId) {
        return billService.getBillsByCustomerId(customerId);
    }

    /**
     * Creates a new bill.
     * Handles: POST http://localhost:8080/api/bills
     */
    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
        Bill newBill = billService.createBill(bill);
        return new ResponseEntity<>(newBill, HttpStatus.CREATED);
    }

    /**
     * Updates an existing bill.
     * Handles: PUT http://localhost:8080/api/bills/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Bill> updateBill(@PathVariable String id, @RequestBody Bill updatedBill) {
        Optional<Bill> bill = billService.updateBill(id, updatedBill);
        return bill.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a bill by its ID.
     * Handles: DELETE http://localhost:8080/api/bills/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable String id) {
        billService.deleteBill(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
