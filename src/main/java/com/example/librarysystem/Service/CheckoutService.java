package com.example.librarysystem.Service;

import com.example.librarysystem.dto.Book;
//import com.example.librarysystem.dto.Checkin;
import com.example.librarysystem.dto.Checkout;
import com.example.librarysystem.dto.Member;

import java.util.List;


public interface CheckoutService {

    Checkout checkoutBook(Checkout checkoutDTO);

    List<Book> getCheckedOutBooks();

    List<Book> getBorrowedBooksByMember(Long memberId);

    List<Member> getMembersWithOverdueBooks();

    double getOverdueAmountForMember(Long memberId);

    boolean checkinBook(Checkout checkinDTO);
}
