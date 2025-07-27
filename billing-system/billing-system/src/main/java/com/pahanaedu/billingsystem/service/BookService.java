// File: src/main/java/com/pahanaedu/billingsystem/service/BookService.java
package com.pahanaedu.billingsystem.service;

import com.pahanaedu.billingsystem.model.Book; // Corrected import
import com.pahanaedu.billingsystem.repository.BookRepository; // Corrected import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marks this as a Spring service component
public class BookService {

    @Autowired
    private BookRepository bookRepository; // Corrected field name

    public List<Book> getAllBooks() { // Corrected method signature (removed extra text)
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(String id) { // Renamed from getProductById
        return bookRepository.findById(id);
    }

    public Book createBook(Book book) { // Renamed from createProduct
        // Business logic: Prevent duplicate ISBNs
        if (book.getIsbn() != null && bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new RuntimeException("Book with ISBN '" + book.getIsbn() + "' already exists.");
        }
        // Ensure stockQuantity is not negative
        if (book.getStockQuantity() < 0) {
            throw new RuntimeException("Stock quantity cannot be negative.");
        }
        return bookRepository.save(book);
    }

    public Book updateBook(String id, Book bookDetails) { // Renamed from updateProduct
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));

        // Update fields
        existingBook.setTitle(bookDetails.getTitle());
        existingBook.setAuthor(bookDetails.getAuthor());
        existingBook.setGenre(bookDetails.getGenre());
        // existingBook.setIsbn(bookDetails.getIsbn()); // ISBN should generally not be changed after creation if it's a unique identifier
        existingBook.setPublisher(bookDetails.getPublisher());
        existingBook.setPublicationYear(bookDetails.getPublicationYear());
        existingBook.setPrice(bookDetails.getPrice());
        existingBook.setStockQuantity(bookDetails.getStockQuantity());

        // Re-check for duplicate ISBN if it was changed (only if you allow ISBN to be updated)
        if (bookDetails.getIsbn() != null && !bookDetails.getIsbn().equals(existingBook.getIsbn())) {
            if (bookRepository.findByIsbn(bookDetails.getIsbn()).isPresent()) {
                throw new RuntimeException("Cannot update: Book with new ISBN '" + bookDetails.getIsbn() + "' already exists.");
            }
            existingBook.setIsbn(bookDetails.getIsbn()); // Apply ISBN update if unique
        }
        // Ensure stockQuantity is not negative
        if (bookDetails.getStockQuantity() < 0) {
            throw new RuntimeException("Stock quantity cannot be negative.");
        }

        return bookRepository.save(existingBook);
    }

    public void deleteBook(String id) { // Renamed from deleteProduct
        // Business logic: You might want to prevent deletion if the book is part of existing bills/orders
        bookRepository.deleteById(id);
    }

    public Optional<Book> getBookByIsbn(String isbn) { // New method for ISBN lookup
        return bookRepository.findByIsbn(isbn);
    }

    public List<Book> getBooksByAuthor(String author) { // New method for author lookup
        return bookRepository.findByAuthor(author);
    }

    public List<Book> getBooksByGenre(String genre) { // New method for genre lookup
        return bookRepository.findByGenre(genre);
    }

    // Method to decrease stock (e.g., when a book is sold)
    public Book decreaseStock(String id, int quantity) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        if (book.getStockQuantity() < quantity) {
            throw new RuntimeException("Not enough stock for book: " + book.getTitle());
        }
        book.setStockQuantity(book.getStockQuantity() - quantity);
        return bookRepository.save(book);
    }

    // Method to increase stock (e.g., when new stock arrives)
    public Book increaseStock(String id, int quantity) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        book.setStockQuantity(book.getStockQuantity() + quantity);
        return bookRepository.save(book);
    }
}
