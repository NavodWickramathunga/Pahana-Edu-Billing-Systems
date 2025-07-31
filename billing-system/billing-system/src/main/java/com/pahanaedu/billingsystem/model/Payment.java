// File: src/main/java/com/pahanaedu/billingsystem/model/Payment.java
package com.pahanaedu.billingsystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate; // For payment date

@Document(collection = "payments") // This maps to a collection named 'payments' in your MongoDB
public class Payment {

    @Id // Marks 'id' as the unique identifier for each payment
    private String id;

    // customerId is a reference to the Customer document's ID
    private String customerId;

    // billId is a reference to the Bill document's ID that this payment is for
    private String billId;

    private LocalDate paymentDate; // The date the payment was made
    private double amount; // The amount of the payment
    private String paymentMethod; // e.g., "Cash", "Card", "Online Transfer"
    private String transactionId; // Optional: A unique ID from the payment gateway/system

    // Constructors
    public Payment() {}

    public Payment(String customerId, String billId, LocalDate paymentDate, double amount, String paymentMethod, String transactionId) {
        this.customerId = customerId;
        this.billId = billId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", billId='" + billId + '\'' +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", transactionId='" + transactionId + '\'' +
                '}';
    }
}
