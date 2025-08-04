// File: src/main/java/com/pahanaedu/billingsystem/controller/BillController.java
package com.pahanaedu.billingsystem.controller;

import com.pahanaedu.billingsystem.model.Bill;
import com.pahanaedu.billingsystem.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    // Existing endpoint to get all bills
    @GetMapping
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    // New endpoint to calculate and generate a bill
    // Handles: POST http://localhost:8080/api/bills/calculate
    @PostMapping("/calculate")
    public ResponseEntity<Bill> calculateBill(@RequestBody BillCalculationRequest request) {
        try {
            Bill newBill = billService.calculateAndCreateBill(request.getCustomerId(), request.getUnitsConsumed(), request.getItemId());
            return new ResponseEntity<>(newBill, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Corrected to remove ambiguity: use status() and build()
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Inner class for the bill calculation request body
    static class BillCalculationRequest {
        private String customerId;
        private int unitsConsumed;
        private String itemId;

        public String getCustomerId() { return customerId; }
        public void setCustomerId(String customerId) { this.customerId = customerId; }
        public int getUnitsConsumed() { return unitsConsumed; }
        public void setUnitsConsumed(int unitsConsumed) { this.unitsConsumed = unitsConsumed; }
        public String getItemId() { return itemId; }
        public void setItemId(String itemId) { this.itemId = itemId; }
    }
}
