// File: src/main/java/com/pahanaedu/billingsystem/model/Product.java
package com.pahanaedu.billingsystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products") // This maps to a collection named 'products' in your MongoDB
public class Product {

    @Id // Marks 'id' as the unique identifier for each product
    private String id;
    private String name; // Name of the product/service (e.g., "Electricity Unit", "Water Bill")
    private String description; // A brief description
    private double unitPrice; // Price per unit (e.g., price per kWh, or fixed price for a service)

    // Constructors
    public Product() {}

    public Product(String name, String description, double unitPrice) {
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
