package com.example.librarysystem.Repository;

import com.example.librarysystem.dto.Book;
import com.example.librarysystem.dto.Checkout;
import com.example.librarysystem.dto.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    @Query("SELECT b FROM Book b JOIN Checkout c ON b.bookId = c.book.bookId WHERE c.member.memberId = :memberId")
    List<Book> findAllBorrowedBooksByMember(@Param("memberId") Long memberId);

    @Query("SELECT DISTINCT c.member FROM Checkout c WHERE c.dueDate < :currentDate AND c.book.checkedOut = false")
    List<Member> findMembersWithOverdueBooks(@Param("currentDate") Date currentDate);
}



