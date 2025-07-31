// File: src/main/java/com/pahanaedu/billingsystem/controller/PaymentController.java
package com.pahanaedu.billingsystem.controller;

import com.pahanaedu.billingsystem.model.Payment;
import com.pahanaedu.billingsystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Marks this as a REST controller
@RequestMapping("/api/payments") // Base path for all payment-related endpoints
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // GET all payments: GET http://localhost:8080/api/payments
    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    // GET payment by ID: GET http://localhost:8080/api/payments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable String id) {
        return paymentService.getPaymentById(id)
                .map(payment -> new ResponseEntity<>(payment, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET payments by Customer ID: GET http://localhost:8080/api/payments/customer/{customerId}
    @GetMapping("/customer/{customerId}")
    public List<Payment> getPaymentsByCustomerId(@PathVariable String customerId) {
        return paymentService.getPaymentsByCustomerId(customerId);
    }

    // GET payments by Bill ID: GET http://localhost:8080/api/payments/bill/{billId}
    @GetMapping("/bill/{billId}")
    public List<Payment> getPaymentsByBillId(@PathVariable String billId) {
        return paymentService.getPaymentsByBillId(billId);
    }

    // CREATE a new payment: POST http://localhost:8080/api/payments
    // Body (JSON): { "customerId": "someCustomerId", "billId": "someBillId", "paymentDate": "YYYY-MM-DD", "amount": 1250.75, "paymentMethod": "Card", "transactionId": "TXN12345" }
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        try {
            Payment newPayment = paymentService.createPayment(payment);
            return new ResponseEntity<>(newPayment, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Handle cases where customer or bill not found, or other business rule violations
            return ResponseEntity.badRequest().body(null); // Return 400 Bad Request with empty body
        }
    }

    // UPDATE an existing payment: PUT http://localhost:8080/api/payments/{id}
    // Body (JSON): { "amount": 1500.00, "paymentMethod": "Online Transfer" }
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable String id, @RequestBody Payment payment) {
        try {
            Payment updatedPayment = paymentService.updatePayment(id, payment);
            return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE a payment: DELETE http://localhost:8080/api/payments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePayment(@PathVariable String id) {
        // Optional: Check if payment exists before attempting to delete.
        // In a real system, you might prevent deletion of settled payments.
        if (paymentService.getPaymentById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            paymentService.deletePayment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}
