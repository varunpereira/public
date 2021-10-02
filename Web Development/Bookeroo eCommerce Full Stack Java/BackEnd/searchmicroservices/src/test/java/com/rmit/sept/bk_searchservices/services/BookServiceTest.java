package com.rmit.sept.bk_searchservices.services;

import com.rmit.sept.bk_searchservices.model.Book;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookServiceTest {
	
	Book book;

    @Autowired
    BookService bookService;

    @BeforeAll
    public void init() {
        // Book saved
        book = new Book();
        book.setName("Harry Potter and the Deathly Hallows");
        book.setAuthor("J.K Rowling");
        book.setIsbn("9780545010221");
        book.setCategory("Fantasy");
        book.setPrice(35.33);
        book.setSeller("Arthur A. Levine Books");
        book.setContenturl("http://www.s3.com/content");
        book.setCoverurl("http://www.s3.com/cover");
        bookService.saveBook(book);
        System.out.println("Book saved");
    }

    @SuppressWarnings("deprecation")
	@Test
    void updateBook() {
        // Update book
        book = new Book();
        book.setName("Harry Potter and the Deathly Hallows");
        book.setAuthor("J.K Rowling");
        book.setIsbn("9780545010221");
        book.setCategory("Fantasy");
        book.setPrice(30.00);
        book.setSeller("Arthur A. Levine Books");
        book.setContenturl("http://www.s3.com/content");
        book.setCoverurl("http://www.s3.com/cover");
        bookService.updateBook(book, "9780545010221");
        assertEquals(30.00, book.getPrice());
        System.out.println("Book updated");
    }

    @Test
     void searchBookName() {
    	 // Finds Book by name 
        String name = "Harry Potter and the Deathly Hallows";
        List<Book> book = bookService.searchBookName(name);
        Book bookOne = book.get(0);
        assertEquals(name, bookOne.getName());
        System.out.println("Book found by name");
    }

    @Test
    void searchBookAuthor() {
    	// Finds Book by author 
        String author = "J.K Rowling";
        List<Book> book = bookService.searchBookAuthor(author);
        Book bookOne = book.get(0);
        assertEquals(author, bookOne.getAuthor());
        System.out.println("Book found by author");
    }

    @Test
    void searchBookIsbn() {
    	// Finds Book by ISBN 
        String isbn = "9780545010221";
        Book book = bookService.searchBookIsbn(isbn);
        assertEquals(isbn, book.getIsbn());
        System.out.println("Book found by isbn");
    }

    @Test
    void searchBookCategory() {
    	// Finds Book by category 
        String category = "Fantasy";
        List<Book> book = bookService.searchBookCategory(category);
        Book bookOne = book.get(0);
        assertEquals(category, bookOne.getCategory());
        System.out.println("Book found by category");
    }


    @Test
    void getSearchedBooks() {
    	// Finds Book by category 
        String bookName = "Harry Potter and the Deathly Hallows";
        List<Book> book = bookService.getSearchedBooks(bookName);
        Book bookOne = book.get(0);
        assertEquals(bookName, bookOne.getName());
        System.out.println("Book found by name, in all search");
    }

    @Test
    void bookExists() {
    	// Check if exists
    	assertEquals(true, bookService.bookExists("9780545010221"));
    	System.out.println("Book exists");
    }

    @Test
    void findAllBooks() {
    	 // Finds all books
        List<Book> books = bookService.findAllBooks();
        assertEquals(1, books.size());
        System.out.println("Books found");
    }
    
    @AfterAll
    void deleteBook() {
        // Deletes book
        bookService.deleteBook("9780545010221");
        System.out.println("Book deleted");
    }
}