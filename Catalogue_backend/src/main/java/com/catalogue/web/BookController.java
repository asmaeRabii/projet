package com.catalogue.web;

import com.catalogue.entities.Book;
import com.catalogue.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book bookDetails) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/searchByTitle")
    public List<Book> searchBookByTitle(@RequestParam String keyword) {
        return bookService.searchBookByTitle(keyword);
    }

    @GetMapping("/searchByAuthor")
    public List<Book> searchBookByAuthor(@RequestParam String author) {
        return bookService.searchBooksByAuthor(author);
    }

    @GetMapping("/searchByCategory")
    public List<Book> searchBookByCategory(@RequestParam String category) {
        return bookService.searchBooksByCategory(category);
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<Boolean> checkBookAvailability(@PathVariable int id) {
        boolean isAvailable = bookService.isBookAvailable(id);
        return ResponseEntity.ok(isAvailable);
    }

    //mise ajour  de stock

    @PutMapping("/{id}/update-stock")
    public ResponseEntity<Book> updateBookStatus(@PathVariable int id, @RequestParam boolean isAvailable) {
        Book updatedBook = bookService.updateBookStatus(id, isAvailable);
        return ResponseEntity.ok(updatedBook);
    }

    @PutMapping("/{id}/loan")
    public ResponseEntity<Book> updateStockOnLoan(@PathVariable int id) {
        try {
            bookService.updateStockOnLoan(id);
            Book updatedBook = bookService.getBookById(id)
                    .orElseThrow(() -> new RuntimeException("Book not found after update"));
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }








}
