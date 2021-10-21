package com.rmit.sept.bk_searchservices.services;

import com.rmit.sept.bk_searchservices.repositories.BookRepository;
import com.rmit.sept.bk_searchservices.exceptions.BookAlreadyExistsException;
import com.rmit.sept.bk_searchservices.model.Book;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // inserts book record into database
    public Book saveBook (Book newBook) {
        try {
            newBook.setName(newBook.getName());
            newBook.setAuthor(newBook.getAuthor());
            newBook.setIsbn(newBook.getIsbn());
            newBook.setCategory(newBook.getCategory());
            newBook.setPrice(newBook.getPrice());
            newBook.setSeller(newBook.getSeller());
            newBook.setStocklevel(newBook.getStocklevel());
            newBook.setLoanedBook(newBook.isLoanedBook());
            newBook.setNewbook(newBook.isNewbook());
            newBook.setCoverurl(newBook.getCoverurl());
            newBook.setContenturl(newBook.getContenturl());
            return bookRepository.save(newBook);

        } catch (Exception e){
            throw new BookAlreadyExistsException("Book with isbn: '" + newBook.getIsbn() + "', already exists");
        }
    }

    // updates book record in the database
    public Book updateBook(Book bookDetails, String isbn) {
        try {
            Book updateBook = bookRepository.findByIsbn(isbn);
            updateBook.setName(bookDetails.getName());
            updateBook.setAuthor(bookDetails.getAuthor());
            updateBook.setIsbn(bookDetails.getIsbn());
            updateBook.setCategory(bookDetails.getCategory());
            updateBook.setPrice(bookDetails.getPrice());
            updateBook.setSeller(bookDetails.getSeller());
            updateBook.setStocklevel(bookDetails.getStocklevel());;
            updateBook.setLoanedBook(bookDetails.isLoanedBook());
            updateBook.setNewbook(bookDetails.isNewbook());
            updateBook.setCoverurl(bookDetails.getCoverurl());;
            updateBook.setContenturl(bookDetails.getContenturl());
            return bookRepository.save(updateBook);
        } catch (Exception e){
            throw new BookAlreadyExistsException("Book with isbn: '" + bookDetails.getIsbn() + "', cannot be updated");
        }
    }

    // finds book records in the database by name
    public List<Book> searchBookName (String name) {
        try {
            List<Book> bookName = bookRepository.findAllByName(name);
            return bookName;
        } catch (Exception e) {
            throw new BookAlreadyExistsException("Book with name: '" + name + "', not found");
        }
    }

    // finds book records in the database by author
    public List<Book> searchBookAuthor (String author) {
        try {
            List<Book> bookAuthor = bookRepository.findAllByAuthor(author);
            return bookAuthor;
        } catch (Exception e) {
            throw new BookAlreadyExistsException("Book with author: '" + author + "', not found");
        }
    }

    // finds book record in the database by isbn
    public Book searchBookIsbn (String isbn) {
        try {
            Book bookIsbn = bookRepository.findByIsbn(isbn);
            return bookIsbn;
        } catch (Exception e) {
            throw new BookAlreadyExistsException("Book with isbn: '" + isbn + "', not found");
        }
    }

    // finds book records in the database by category
    public List<Book> searchBookCategory (String category) {
        try {
            List<Book> bookCategory = bookRepository.findAllByCategory(category);
            return bookCategory;
        } catch (Exception e) {
            throw new BookAlreadyExistsException("Book with category: '" + category + "', not found");
        }
    }

    // deletes a book record in the database by isbn
    public void deleteBook(String isbn) {
        try {
            Book book = bookRepository.findByIsbn(isbn);
            bookRepository.delete(book);
        } catch (Exception e){
            throw new BookAlreadyExistsException("Book with isbn: '" + isbn + "', not found");
        }
    }

    // finds all book records in the database by all filter options
    public List<Book> getSearchedBooks(String keyword) {
        if (keyword != null) {
            return bookRepository.searchAllBooks(keyword);
        }
        return (List<Book>) bookRepository.findAll();
    }
    
    // checks stock level
    public boolean stockExists(String isbn, Integer amount) {
    	try {
    		Book book = bookRepository.findByIsbn(isbn);
	    	Integer stockLevel = book.getStocklevel();
	    	if(amount <= stockLevel) {
	    		return true;
	    	} else {
	    		return false;
	    	} } catch (Exception e) {
	    	throw new BookAlreadyExistsException("Book doesn't exist or incorrect stock level");
    }
  }
    
    // decrease stock level by amount - after successful payment
    public Book decreaseStock(String isbn, Integer amount) {
    	try {
	    	Book book = bookRepository.findByIsbn(isbn);
	    	System.out.println(book.getIsbn());
	    	Integer stockLevel = book.getStocklevel();
	    	Integer result = stockLevel - amount;
	    	book.setStocklevel(result);
	        return bookRepository.save(book);
    	} catch (Exception e) {
    		throw new BookAlreadyExistsException("Book doesn't exist or cannot decrease stock by given amount");
    	}	
    }
    
    // increase stock level by amount - after successful refund
    public Book IncreaseStock(String isbn, Integer amount) {
    	try {
	    	Book book = bookRepository.findByIsbn(isbn);
	    	Integer stockLevel = book.getStocklevel();
	    	Integer result = stockLevel + amount;
	    	book.setStocklevel(result);
	        return bookRepository.save(book);
    	} catch (Exception e) {
    		throw new BookAlreadyExistsException("Book doesn't exist or cannot increase stock by given amount");
    	}
    }
   
    // find if a book exists
    public boolean bookExists(String isbn){
        return bookRepository.existsByIsbn(isbn);
    }

    // finds all book records in the database
    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }
     
   // Basic info book list
   public void quickBookListCsv(Writer writer) {
    	List<Book> books = bookRepository.findAll();
    	try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)){
    			for (Book book: books) {
    				csvPrinter.printRecord(book.getName(), book.getAuthor(), book.getIsbn(), book.getCategory());
    			}
    		} catch (IOException e) {
    			throw new BookAlreadyExistsException("CSV cannot be generated");
    		}
    	}
   
   // Detailed info book list
   public void detailedBookListCsv(Writer writer) {
    	List<Book> books = bookRepository.findAll();
    	try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)){
    			for (Book book: books) {
    				csvPrinter.printRecord(book.getName(), book.getIsbn(), book.getPrice(), book.getSeller(), book.getStocklevel());
    			}
    		} catch (IOException e) {
    			throw new BookAlreadyExistsException("CSV cannot be generated");
    		}
    	}
 }
