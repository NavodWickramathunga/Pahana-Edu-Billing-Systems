// File: src/main/java/com/pahanaedu/billingsystem/service/PaymentService.java
package com.pahanaedu.billingsystem.service;

import com.pahanaedu.billingsystem.model.Payment;
import com.pahanaedu.billingsystem.model.Customer; // To validate customer existence
import com.pahanaedu.billingsystem.model.Bill;     // To validate bill existence and potentially update status
import com.pahanaedu.billingsystem.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marks this as a Spring service component
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerService customerService; // To validate customer IDs

    @Autowired
    private BillService billService; // To validate bill IDs and update bill status

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(String id) {
        return paymentRepository.findById(id);
    }

    public Payment createPayment(Payment payment) {
        // Business logic: Validate customer and bill existence
        Optional<Customer> customer = customerService.getCustomerById(payment.getCustomerId());
        if (customer.isEmpty()) {
            throw new RuntimeException("Customer with ID " + payment.getCustomerId() + " not found. Cannot create payment.");
        }

        Optional<Bill> bill = billService.getBillById(payment.getBillId());
        if (bill.isEmpty()) {
            throw new RuntimeException("Bill with ID " + payment.getBillId() + " not found. Cannot create payment.");
        }

        // Optional: Update bill status to PAID if the payment amount covers the bill
        // This is a simplified example; a real system might handle partial payments
        if (payment.getAmount() >= bill.get().getAmount() && !bill.get().getStatus().equals("PAID")) {
            Bill existingBill = bill.get();
            existingBill.setStatus("PAID");
            billService.updateBill(existingBill.getId(), existingBill); // Update the bill status
        }

        return paymentRepository.save(payment);
    }

    public Payment updatePayment(String id, Payment paymentDetails) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id " + id));

        // Update fields that are allowed to be changed
        existingPayment.setPaymentDate(paymentDetails.getPaymentDate());
        existingPayment.setAmount(paymentDetails.getAmount());
        existingPayment.setPaymentMethod(paymentDetails.getPaymentMethod());
        existingPayment.setTransactionId(paymentDetails.getTransactionId());

        // Do NOT allow changing customerId or billId directly in an update, typically.
        // If a payment needs to be re-assigned, it's usually a new payment record.

        return paymentRepository.save(existingPayment);
    }

    public void deletePayment(String id) {
        paymentRepository.deleteById(id);
    }

    public List<Payment> getPaymentsByCustomerId(String customerId) {
        return paymentRepository.findByCustomerId(customerId);
    }

    public List<Payment> getPaymentsByBillId(String billId) {
        return paymentRepository.findByBillId(billId);
    }
}
