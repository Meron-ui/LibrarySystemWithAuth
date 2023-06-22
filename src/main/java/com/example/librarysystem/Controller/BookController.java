package com.example.librarysystem.Controller;

import com.example.librarysystem.Service.BookService;
import com.example.librarysystem.dto.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Book> addBook(@Validated @RequestBody Book bookDTO) {
        Book book = bookService.addBook(bookDTO);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @Validated @RequestBody Book bookDTO) {
        Book updatedBook = bookService.updateBook(bookId, bookDTO);
        if (updatedBook != null) {
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        boolean isDeleted = bookService.deleteBook(bookId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/searchByAuthor")
    public ResponseEntity<List<Book>> searchBooksByAuthor(@RequestParam("author") String author) {
        List<Book> books = bookService.searchBooksByAuthor(author);
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(books);
        }
    }

    @GetMapping("/searchByCategory")
    public ResponseEntity<List<Book>> searchBooksByCategory(@RequestParam("category") String category) {
        List<Book> books = bookService.searchBooksByCategory(category);
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(books);
        }
    }

    @GetMapping("/searchByTitle")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam("title") String title) {
        List<Book> books = bookService.searchBooksByTitle(title);
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(books);
        }
    }


    @GetMapping("/sortedByAuthor")
    public ResponseEntity<List<Book>> getBooksSortedByAuthor() {
        List<Book> books = bookService.getBooksSortedByAuthor();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/sortedByCategory")
    public ResponseEntity<List<Book>> getBooksSortedByCategory() {
        List<Book> books = bookService.getBooksSortedByCategory();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/sortedByTitle")
    public ResponseEntity<List<Book>> getBooksSortedByTitle() {
        List<Book> books = bookService.getBooksSortedByTitle();
        return ResponseEntity.ok(books);
    }

}
