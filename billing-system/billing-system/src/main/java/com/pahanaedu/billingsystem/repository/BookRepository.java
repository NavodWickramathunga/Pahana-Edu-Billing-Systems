// File: src/main/java/com/pahanaedu/billingsystem/repository/BookRepository.java
package com.pahanaedu.billingsystem.repository;

import com.pahanaedu.billingsystem.model.Book; // Import your Book model
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List; // Added for findByAuthor

@Repository // Marks this as a Spring component for data access
public interface BookRepository extends MongoRepository<Book, String> {
    // MongoRepository provides basic CRUD operations for Book documents.

    // Custom query method to find a book by its ISBN (should be unique)
    Optional<Book> findByIsbn(String isbn); // Corrected method signature (removed extra text)

    // Custom query method to find books by author
    List<Book> findByAuthor(String author);

    // Custom query method to find books by genre
    List<Book> findByGenre(String genre);
}
