// File: src/main/java/com/pahanaedu/billingsystem/dto/CustomerDetailsDto.java
package com.pahanaedu.billingsystem.dto;

import com.pahanaedu.billingsystem.model.Bill;
import com.pahanaedu.billingsystem.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) for combining Customer details and their Bills.
 * This is the object that will be returned by the API endpoint to the frontend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailsDto {

    private Customer customer;
    private List<Bill> bills;

}
