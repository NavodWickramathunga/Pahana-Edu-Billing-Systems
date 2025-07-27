// File: src/main/java/com/pahanaedu/billingsystem/model/Bill.java
package com.pahanaedu.billingsystem.model; // This is the correct package for the model

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate; // For dates

@Document(collection = "bills") // This maps to a collection named 'bills' in your MongoDB
public class Bill {

    @Id // Marks 'id' as the unique identifier for each bill
    private String id;

    // customerId is a reference to the Customer document's ID
    private String customerId;

    private LocalDate billDate; // The date the bill was generated
    private LocalDate dueDate; // The date the bill is due
    private double amount; // Total amount of the bill
    private String status; // e.g., "UNPAID", "PAID", "OVERDUE"
    private int unitsConsumed; // Electricity units consumed for this bill period
    private double meterReading; // The meter reading at the time of billing

    // Constructors
    public Bill() {}

    public Bill(String customerId, LocalDate billDate, LocalDate dueDate, double amount, String status, int unitsConsumed, double meterReading) {
        this.customerId = customerId;
        this.billDate = billDate;
        this.dueDate = dueDate;
        this.amount = amount;
        this.status = status;
        this.unitsConsumed = unitsConsumed;
        this.meterReading = meterReading;
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

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUnitsConsumed() {
        return unitsConsumed;
    }

    public void setUnitsConsumed(int unitsConsumed) {
        this.unitsConsumed = unitsConsumed;
    }

    public double getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(double meterReading) {
        this.meterReading = meterReading;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", billDate=" + billDate +
                ", dueDate=" + dueDate +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", unitsConsumed=" + unitsConsumed +
                ", meterReading=" + meterReading +
                '}';
    }
}
