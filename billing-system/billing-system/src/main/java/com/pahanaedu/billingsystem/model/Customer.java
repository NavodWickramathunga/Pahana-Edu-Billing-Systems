// File: src/main/java/com/pahanaedu/billingsystem/model/Customer.java
package com.pahanaedu.billingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data // Generates getters, setters, toString, equals, and hashCode
@Document(collection = "customers")
public class Customer {
    @Id
    private String id;

    private String name;
    private String address;
    private String mobileNumber;
    private String telephoneNumber;
    private double unitsConsumed;

    @JsonIgnore // Ensures the password is not sent back in API responses
    private String password;
}
