package com.example.librarysystem.Controller;

import com.example.librarysystem.Service.CheckoutService;
import com.example.librarysystem.dto.Book;
import com.example.librarysystem.dto.Checkout;
import com.example.librarysystem.dto.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @PostMapping("/checkoutBook")
    public ResponseEntity<Checkout> checkoutBook(@Validated @RequestBody Checkout checkoutDTO) {
        Checkout checkout = checkoutService.checkoutBook(checkoutDTO);
        return ResponseEntity.ok(checkout);
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @GetMapping("/checkedout")
    public List<Book> getCheckedOutBooks() {
        return checkoutService.getCheckedOutBooks();
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @GetMapping("/borrowed/{memberId}")
    public List<Book> getBorrowedBooksByMember(@PathVariable Long memberId) {
        return checkoutService.getBorrowedBooksByMember(memberId);
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @GetMapping("/overdue")
    public List<Member> getMembersWithOverdueBooks() {
        return checkoutService.getMembersWithOverdueBooks();
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    @GetMapping("/overdue/{memberId}")
    public double getOverdueAmountForMember(@PathVariable Long memberId) {
        return checkoutService.getOverdueAmountForMember(memberId);
    }

}
