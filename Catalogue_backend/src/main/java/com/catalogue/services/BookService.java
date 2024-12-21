package com.catalogue.services;

import com.catalogue.entities.Book;
import com.catalogue.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(int id) {
        return bookRepository.findById(id);
    }


    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(int id, Book bookDetails) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setDescription(bookDetails.getDescription());
        book.setStatus(bookDetails.getStatus());
        book.setImageurl(bookDetails.getImageurl());
        return bookRepository.save(book);
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBookByTitle(String keyword) {
        return bookRepository.findByTitleContaining(keyword);
    }
    public List<Book> searchBooksByAuthor(String author) {
        List<Book> allBooks = bookRepository.findAll();
        return allBooks.stream()
                .filter(book -> book.getAuthor() != null && book.getAuthor().contains(author))
                .collect(Collectors.toList());
    }

    public List<Book> searchBooksByCategory(String category) {
        List<Book> allBooks = bookRepository.findAll();
        return allBooks.stream()
                .filter(book -> book.getCategory() != null && book.getCategory().contains(category))
                .collect(Collectors.toList());
    }

    public boolean isBookAvailable(int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return book.getStatus();
    }

    //mise a jour du stock .appeler par Emprunt
    public Book updateBookStatus(int id, boolean isAvailable) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setStatus(isAvailable);
        return bookRepository.save(book);
    }

    public void updateStockOnLoan(int id) {
        Book book = getBookById(id).orElseThrow();
        if (book.getStock() <= 0) {
            throw new RuntimeException("Stock épuisé");
        }
        book.setStock(book.getStock() - 1);
        bookRepository.save(book);
    }




}
