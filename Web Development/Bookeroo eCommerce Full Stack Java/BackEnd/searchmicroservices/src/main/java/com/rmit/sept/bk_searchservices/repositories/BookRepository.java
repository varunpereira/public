package com.rmit.sept.bk_searchservices.repositories;

import com.rmit.sept.bk_searchservices.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    // Finds all books by name
    List<Book> findAllByName(String name);
    // Finds all books by author
    List<Book> findAllByAuthor(String author);
    // Finds a books by ISBN
    Book findByIsbn(String isbn);
    // Finds all books by category
    List<Book> findAllByCategory(String category);
    // Delete book
    void delete(Book book);
    // Finds all books by all filter options
    @Query("SELECT b FROM Book b WHERE b.name LIKE %?1%"
            + " OR b.author LIKE %?1%"
            + " OR b.isbn LIKE %?1%"
            + " OR b.category LIKE %?1%")
    List<Book> searchAllBooks(String keyword);
    // finds all books
    List<Book> findAll();
    // checks if the book exists
    boolean existsByIsbn(String isbn);
    // Finds the book by isbn and book type
    Book findByIsbnAndNewbook(String isbn, boolean newbook);
}
