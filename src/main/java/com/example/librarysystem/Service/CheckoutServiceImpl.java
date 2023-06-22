package com.example.librarysystem.Service;

import com.example.librarysystem.Repository.BookRepository;
import com.example.librarysystem.Repository.CheckoutRepository;
import com.example.librarysystem.Repository.MemberRepository;
import com.example.librarysystem.dto.Book;
import com.example.librarysystem.dto.Checkout;
import com.example.librarysystem.dto.Member;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CheckoutServiceImpl implements CheckoutService {


    private final CheckoutRepository checkoutRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public CheckoutServiceImpl(CheckoutRepository checkoutRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.checkoutRepository = checkoutRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }


    @Override
    public Checkout checkoutBook(Checkout checkoutDTO) {
        try {
            Optional<Book> book = bookRepository.findById(checkoutDTO.getBook().getBookId());
            Optional<Member> member = memberRepository.findById(checkoutDTO.getMember().getMemberId());

            if (book.isPresent()) {
                if (member.isPresent()) {
                    Book checkedBook = book.get();
                    Member borrowingMember = member.get();

                    if (!checkedBook.isCheckedOut()) {
                        if (borrowingMember.getFees() < 1.00) {
                            if (borrowingMember.getCategory().equals("STAFF") || borrowingMember.getCategory().equals("STANDARD")) {
                                // Update the book's status and due date
                                checkedBook.setCheckedOut(true);

                                Checkout checkout = new Checkout();
                                checkout.setBook(checkedBook);
                                checkout.setMember(borrowingMember);

                                Date currentDate = new Date();
                                checkout.setCheckoutDate(currentDate);

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(currentDate);
                                calendar.add(Calendar.DAY_OF_MONTH, 21);

                                Date dueDate = calendar.getTime();
                                checkout.setDueDate(dueDate);

                                return checkoutRepository.save(checkout);
                            } else if (borrowingMember.getCategory().equals("SENIOR_CITIZENS")) {
                                // Update the book's status and due date
                                checkedBook.setCheckedOut(true);

                                Checkout checkout = new Checkout();
                                checkout.setBook(checkedBook);
                                checkout.setMember(borrowingMember);

                                Date currentDate = new Date();
                                checkout.setCheckoutDate(currentDate);

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(currentDate);
                                calendar.add(Calendar.DAY_OF_MONTH, 42);
                                Date dueDate = calendar.getTime();
                                checkout.setDueDate(dueDate);

                                return checkoutRepository.save(checkout);
                            } else {
                                System.out.println("Only staff, standard, or senior citizen members are allowed to checkout books.");
                                throw new IllegalArgumentException("Only staff, standard, or senior citizen members are allowed to checkout books.");
                            }
                        } else {
                            System.out.println("member has unpaid fees");
                            throw new IllegalArgumentException("member has unpaid fees");
                        }

                    } else {
                        System.out.println("Book is not available");
                        throw new IllegalArgumentException("Book is not available");
                    }

                } else {
                    System.out.println("member is not found in the system");
                    throw new IllegalArgumentException("member is not found in the system");
                }
            } else {
                System.out.println("Book is not found in the system");
                throw new IllegalArgumentException("Book is not found in the system");
            }

        } catch (IllegalArgumentException e) {
            // Handle the IllegalArgumentException (invalid category or unpaid fees)
            System.out.println("Invalid Input");
            return null; // or return an error response indicating the reason for failure
        } catch (NoSuchElementException e) {
            // Handle the NoSuchElementException (book or member not found)
            System.out.println("Invalid Input");
            return null; // or return an error response indicating the reason for failure
        }
    }

    @Override
    public List<Book> getCheckedOutBooks() {
        return bookRepository.findAllCheckedoutBooks();
    }

    @Override
    public List<Book> getBorrowedBooksByMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        if (member.isPresent()) {
            return checkoutRepository.findAllBorrowedBooksByMember(memberId);
        } else {
            System.out.println("Member Not found");
        }
        return null;
    }

    @Override
    public List<Member> getMembersWithOverdueBooks() {
        Date currentDate = new Date();
        return checkoutRepository.findMembersWithOverdueBooks(currentDate);
    }

    @Override
    public double getOverdueAmountForMember(Long memberId) {

        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isPresent()) {
            return memberRepository.findOverdueAmount(memberId);
        } else {
            System.out.println("Member Not found");
        }
        return 0;
    }

    @Override
    public boolean checkinBook(Checkout checkinDTO) {
        Optional<Checkout> checkout = checkoutRepository.findById(checkinDTO.getCheckoutId());

        if (checkout.isPresent()) {
            Checkout checkedOutBook = checkout.get();
            Book book = checkedOutBook.getBook();
            Member member = checkedOutBook.getMember();

            if (!book.isCheckedOut()) {
                // Book has already been returned
                return false;
            }

            // Update the book's status
            book.setCheckedOut(false);

            // Calculate the overdue fee based on the member's category
            double overdueFee = calculateOverdueFee(member, checkedOutBook);

            // Update the member's fee amount
            double memberFee = member.getFees();
            member.setFees(memberFee + overdueFee);

            // Save the updated book, checkout, and member entities
            bookRepository.save(book);
            checkoutRepository.delete(checkedOutBook);
            memberRepository.save(member);

            return true;
        }
        else{
            System.out.println("checkout is not present");
        }

        return false;
    }

    private double calculateOverdueFee(Member member, Checkout checkout) {
        double feePerDay;

        switch (member.getCategory()) {
            case "STANDARD":
                feePerDay = 0.25;
                break;
            case "STAFF":
                feePerDay = 0.10;
                break;
            case "SENIOR_CITIZENS":
                feePerDay = 0.05;
                break;
            default:
                feePerDay = 0.0;
                break;
        }

        Date dueDate = checkout.getDueDate();
        Date currentDate = new Date();

        // Calculate the number of days overdue
        long daysOverdue = TimeUnit.MILLISECONDS.toDays(currentDate.getTime() - dueDate.getTime());
        if (daysOverdue < 0) {
            daysOverdue = 0; // No overdue fee if returned before or on the due date
        }

        return feePerDay * daysOverdue;
    }

}
