package com.rmit.sept.bk_searchservices.web;

import com.rmit.sept.bk_searchservices.model.Book;
import com.rmit.sept.bk_searchservices.services.BookService;
import com.rmit.sept.bk_searchservices.services.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private BookService bookService;
    
    
   // For user interaction
    
   // search books by all filter options
    @GetMapping("/search/all/{keyword}")
    public List<Book> viewAllBooks(@PathVariable(value = "keyword") String keyword) {
        return bookService.getSearchedBooks(keyword);
    }
    
    // search books by name
    @GetMapping("/search/name/{name}")
    public List<Book> searchBookByName(@PathVariable(value = "name") String name) {
        return bookService.searchBookName(name);
    }

    // search books by author
    @GetMapping("/search/author/{author}")
    public List<Book> searchBookByAuthor(@PathVariable(value = "author") String  author) {
        return bookService.searchBookAuthor(author);
    }

    // search book by isbn
    @GetMapping("/search/isbn/{isbn}")
    public Book searchBookByIsbn(@PathVariable(value = "isbn") String isbn) {
        return bookService.searchBookIsbn(isbn);
    }

    // search books by category
    @GetMapping("/search/category/{category}")
    public List<Book> searchBookByCategory(@PathVariable(value = "category") String category) {
        return bookService.searchBookCategory(category);
    }
    
    // Before purchase add to cart
    
    // checks the level of stock
    @PostMapping("/checkStock/{isbn}/{amount}")
    public boolean checkStockLevel(@PathVariable(value = "isbn") String isbn, @PathVariable(value = "amount") Integer amount){
    	return bookService.stockExists(isbn, amount);
    }
    
    // After PayPal purchase
    
    // decrease stock level
    @PatchMapping("/decreaseStock/{isbn}/{amount}")
    public ResponseEntity<?> decreaseStockAmount(@PathVariable(value = "isbn") String isbn, 
    											 @PathVariable(value = "amount") Integer amount){
    	
        bookService.decreaseStock(isbn, amount);
        
        return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
    }
    
    // For Admin

    // adds book
    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@Valid @RequestBody Book book, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        Book newBook = bookService.saveBook(book);

        return new ResponseEntity<Book>(newBook, HttpStatus.CREATED);
    }

    // updates book
    @PatchMapping("/updateBook/{isbn}")
    public ResponseEntity<?> updateBookDetails(@PathVariable(value = "isbn") String isbn,
                                        @Valid @RequestBody Book book, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        Book updateBook = bookService.updateBook(book, isbn);

        return new ResponseEntity<Book>(updateBook, HttpStatus.CREATED);
    }

    // deletes book by isbn
    @DeleteMapping("/deleteBook/{isbn}")
    public ResponseEntity<String> deleteBookByISBN (@PathVariable(value = "isbn") String isbn) {
        bookService.deleteBook(isbn);
        return new ResponseEntity<String>("Book with ISBN: " + isbn  + " deleted", HttpStatus.OK);
    }

    // For Books 
    
    // returns all books
    @GetMapping("/search/allBooks")
    public List<Book> viewAllBooks(){
        return bookService.findAllBooks();
    }
    
    // Before adding the order

    // verifies if a book exists
    @PostMapping("/bookExists/{isbn}")
    public ResponseEntity<?> checkBookExists(@PathVariable(value = "isbn") String isbn){
        if(bookService.bookExists(isbn)){
            return new ResponseEntity<String>("Exists", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Book does not exist", HttpStatus.OK);
        }
    }
    
    // After refund
    
    // increase stock level
    @PatchMapping("/increaseStock/{isbn}/{amount}")
    public ResponseEntity<?> increaseStockAmount(@PathVariable(value = "isbn") String isbn, 
    											 @PathVariable(value = "amount") Integer amount){
    	
        bookService.IncreaseStock(isbn, amount);
        
        return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
    }
    
    // Download csv file for Basic Book List
    @GetMapping("/bookListBasic")
    public void getBookBasic(HttpServletResponse servletResponse) throws IOException  {
    	servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"basicBookList.csv\"");
        bookService.quickBookListCsv(servletResponse.getWriter());
    }
    
    // Download csv file for Detailed Book List
    @GetMapping("/bookListDetailed")
    public void getBookDetailed(HttpServletResponse servletResponse) throws IOException  {
    	servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"detailedBookList.csv\"");
        bookService.detailedBookListCsv(servletResponse.getWriter());
    }
    
    
}
