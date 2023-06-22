package com.example.librarysystem.Service;

import com.example.librarysystem.dto.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface BookService {
    List<Book> getAllBooks();

    Book addBook(Book bookDTO);

    Book getBookById(Long bookId);

    Book updateBook(Long bookId, Book bookDTO);

    boolean deleteBook(Long bookId);

    List<Book> searchBooksByCategory(String category);

    List<Book> searchBooksByAuthor(String author);

    List<Book> searchBooksByTitle(String title);

    List<Book> getBooksSortedByAuthor();

    List<Book> getBooksSortedByCategory();

    List<Book> getBooksSortedByTitle();

}
