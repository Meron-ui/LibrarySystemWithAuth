package com.example.librarysystem.Repository;

import com.example.librarysystem.dto.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {


    @Query("SELECT b FROM Book b WHERE b.category = :category")
    List<Book> findByCategory(@Param("category") String category);

    @Query("SELECT b FROM Book b WHERE b.author = :author")
    List<Book> findByAuthor(@Param("author") String author);

    @Query("SELECT b FROM Book b WHERE b.title = :title")
    List<Book> findByTitle(@Param("title") String title);

    @Query("SELECT b FROM Book b ORDER BY b.author")
    List<Book> findAllByOrderByAuthor();

    @Query("SELECT b FROM Book b ORDER BY b.category")
    List<Book> findAllByOrderByCategory();

    @Query("SELECT b FROM Book b ORDER BY b.title")
    List<Book> findAllByOrderByTitle();

    @Query("SELECT b FROM Book b WHERE b.checkedOut = true")
    List<Book> findAllCheckedoutBooks();
}
