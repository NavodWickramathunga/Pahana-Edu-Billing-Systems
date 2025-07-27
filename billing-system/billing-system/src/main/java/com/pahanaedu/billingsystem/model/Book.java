// File: src/main/java/com/pahanaedu/billingsystem/model/Book.java
package com.pahanaedu.billingsystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books") // This maps to a collection named 'books' in your MongoDB
public class Book {

    @Id // Marks 'id' as the unique identifier for each book
    private String id;
    private String title; // Title of the book
    private String author; // Author of the book
    private String genre; // Genre of the book (e.g., "Fiction", "Science")
    private String isbn; // International Standard Book Number (unique identifier for books)
    private String publisher; // Publisher of the book
    private int publicationYear; // Year the book was published
    private double price; // Price of the book
    private int stockQuantity; // Number of copies available in stock

    // Constructors
    public Book() {}

    public Book(String title, String author, String genre, String isbn, String publisher, int publicationYear, double price, int stockQuantity) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publicationYear=" + publicationYear +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
