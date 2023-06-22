package com.example.librarysystem.Service;

import com.example.librarysystem.Repository.BookRepository;
import com.example.librarysystem.dto.Book;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    @Override
    public Book addBook(Book bookDTO) {
        Book book = new Book();
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setDescription(bookDTO.getDescription());
        book.setLocation(bookDTO.getLocation());
        book.setCategory(bookDTO.getCategory());
        book.setCheckedOut(bookDTO.isCheckedOut());
        book.setPublisher(bookDTO.getPublisher());
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    @Override
    public Book updateBook(Long bookId, Book bookDTO) {
        Optional<Book> entity= bookRepository.findById(bookId);
        if(entity.isPresent()){
            Book book = entity.get();
            book.setIsbn(bookDTO.getIsbn());
            book.setTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor());
            book.setDescription(bookDTO.getDescription());
            book.setLocation(bookDTO.getLocation());
            book.setCategory(bookDTO.getCategory());
            book.setCheckedOut(bookDTO.isCheckedOut());
            return bookRepository.save(book);
        }
        return null;
    }

    @Override
    public boolean deleteBook(Long bookId) {
        Book existingBook = getBookById(bookId);
        if (existingBook != null) {
            bookRepository.delete(existingBook);
            return true;
        }
        return false;
    }

    @Override
    public List<Book> searchBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    @Override
    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> getBooksSortedByAuthor() {
        return bookRepository.findAllByOrderByAuthor();
    }

    @Override
    public List<Book> getBooksSortedByCategory() {
        return bookRepository.findAllByOrderByCategory();
    }

    @Override
    public List<Book> getBooksSortedByTitle() {
        return bookRepository.findAllByOrderByTitle();
    }

}
