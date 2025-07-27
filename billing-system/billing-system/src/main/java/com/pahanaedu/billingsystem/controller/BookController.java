// File: src/main/java/com/pahanaedu/billingsystem/controller/BookController.java
package com.pahanaedu.billingsystem.controller;

import com.pahanaedu.billingsystem.model.Book;
import com.pahanaedu.billingsystem.service.BookService; // Ensure this imports BookService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional; // Added for Optional in getBookById

@RestController // Marks this as a REST controller
@RequestMapping("/api/books") // Base path for all book-related endpoints
public class BookController {

    @Autowired
    private BookService bookService; // Injected BookService (renamed from productService)

    // GET all books: GET http://localhost:8080/api/books
    @GetMapping
    public List<Book> getAllBooks() { // Method name changed to reflect 'Book'
        return bookService.getAllBooks(); // Calling getAllBooks on bookService
    }

    // GET book by ID: GET http://localhost:8080/api/books/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) { // Method name changed to reflect 'Book'
        return bookService.getBookById(id) // Calling getBookById on bookService
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET book by ISBN: GET http://localhost:8080/api/books/isbn/{isbn}
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn)
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET books by Author: GET http://localhost:8080/api/books/author/{author}
    @GetMapping("/author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        return bookService.getBooksByAuthor(author);
    }

    // GET books by Genre: GET http://localhost:8080/api/books/genre/{genre}
    @GetMapping("/genre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return bookService.getBooksByGenre(genre);
    }

    // CREATE a new book: POST http://localhost:8080/api/books
    // Body (JSON): { "title": "The Great Novel", "author": "Jane Doe", "genre": "Fiction", "isbn": "978-1234567890", "publisher": "Awesome Books", "publicationYear": 2023, "price": 25.99, "stockQuantity": 100 }
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) { // Method name changed to reflect 'Book'
        try {
            Book newBook = bookService.createBook(book); // Calling createBook on bookService
            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // e.g., if ISBN already exists or stock quantity is negative
            return ResponseEntity.badRequest().body(null); // Return 400 Bad Request with empty body
        }
    }

    // UPDATE an existing book: PUT http://localhost:8080/api/books/{id}
    // Body (JSON): { "title": "The Great Novel (Revised)", "price": 27.50, "stockQuantity": 95 }
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody Book book) { // Method name changed to reflect 'Book'
        try {
            Book updatedBook = bookService.updateBook(id, book); // Calling updateBook on bookService
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 if book not found, or 400 if ISBN conflict
        }
    }

    // DELETE a book: DELETE http://localhost:8080/api/books/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable String id) { // Method name changed to reflect 'Book'
        // Optional: Check if book exists before attempting to delete
        if (bookService.getBookById(id).isEmpty()) { // Calling getBookById on bookService
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            bookService.deleteBook(id); // Calling deleteBook on bookService
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    // Endpoint to decrease stock: PUT http://localhost:8080/api/books/{id}/decreaseStock/{quantity}
    @PutMapping("/{id}/decreaseStock/{quantity}")
    public ResponseEntity<Book> decreaseBookStock(@PathVariable String id, @PathVariable int quantity) {
        try {
            Book updatedBook = bookService.decreaseStock(id, quantity);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Catch specific exceptions like "Not enough stock"
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }

    // Endpoint to increase stock: PUT http://localhost:8080/api/books/{id}/increaseStock/{quantity}
    @PutMapping("/{id}/increaseStock/{quantity}")
    public ResponseEntity<Book> increaseBookStock(@PathVariable String id, @PathVariable int quantity) {
        try {
            Book updatedBook = bookService.increaseStock(id, quantity);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
    }
}
