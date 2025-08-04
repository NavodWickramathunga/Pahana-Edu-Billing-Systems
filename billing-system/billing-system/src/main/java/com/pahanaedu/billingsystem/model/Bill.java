// File: src/main/java/com/pahanaedu/billingsystem/model/Bill.java
package com.pahanaedu.billingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bills")
public class Bill {

    @Id
    private String id;
    private String customerId;
    private LocalDate billDate;
    private LocalDate dueDate;
    private double amount;
    private String status; // e.g., "UNPAID", "PAID"
    private int unitsConsumed;
    private double meterReading; // Added for more comprehensive billing

}
