// File: src/main/java/com/pahanaedu/billingsystem/repository/BillRepository.java
package com.pahanaedu.billingsystem.repository;

import com.pahanaedu.billingsystem.model.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends MongoRepository<Bill, String> {

    /**
     * Finds all bills associated with a specific customer ID.
     * @param customerId The ID of the customer.
     * @return A list of bills for the given customer.
     */
    List<Bill> findByCustomerId(String customerId);
}
