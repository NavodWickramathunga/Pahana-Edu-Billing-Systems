// File: src/main/java/com/pahanaedu/billingsystem/controller/BillController.java
package com.pahanaedu.billingsystem.controller;

import com.pahanaedu.billingsystem.model.Bill;
import com.pahanaedu.billingsystem.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional; // Added for Optional in getBillById

@RestController // Marks this as a REST controller
@RequestMapping("/api/bills") // Base path for all bill-related endpoints
public class BillController {

    @Autowired
    private BillService billService;

    // GET all bills: GET http://localhost:8080/api/bills
    @GetMapping
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    // GET bill by ID: GET http://localhost:8080/api/bills/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable String id) {
        return billService.getBillById(id)
                .map(bill -> new ResponseEntity<>(bill, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET bills by Customer ID: GET http://localhost:8080/api/bills/customer/{customerId}
    @GetMapping("/customer/{customerId}")
    public List<Bill> getBillsByCustomerId(@PathVariable String customerId) {
        return billService.getBillsByCustomerId(customerId);
    }

    // GET bills by Status: GET http://localhost:8080/api/bills/status/{status}
    @GetMapping("/status/{status}")
    public List<Bill> getBillsByStatus(@PathVariable String status) {
        return billService.getBillsByStatus(status);
    }

    // CREATE a new bill: POST http://localhost:8080/api/bills
    // Body (JSON): { "customerId": "someCustomerId", "billDate": "YYYY-MM-DD", "dueDate": "YYYY-MM-DD", "amount": 123.45, "status": "UNPAID", "unitsConsumed": 200, "meterReading": 12345.67 }
    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
        try {
            Bill newBill = billService.createBill(bill);
            return new ResponseEntity<>(newBill, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Handle cases where customer not found or other business rule violations
            return ResponseEntity.badRequest().body(null); // Return 400 Bad Request with empty body
        }
    }

    // UPDATE an existing bill: PUT http://localhost:8080/api/bills/{id}
    // Body (JSON): { "amount": 150.00, "status": "PAID" }
    @PutMapping("/{id}")
    public ResponseEntity<Bill> updateBill(@PathVariable String id, @RequestBody Bill bill) {
        try {
            Bill updatedBill = billService.updateBill(id, bill);
            return new ResponseEntity<>(updatedBill, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE a bill: DELETE http://localhost:8080/api/bills/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBill(@PathVariable String id) {
        // First, check if the bill exists to provide a meaningful 404 response.
        if (billService.getBillById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            billService.deleteBill(id);
            // If deletion is successful, return 204 No Content.
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Catch other potential exceptions during deletion, e.g., database issues.
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
