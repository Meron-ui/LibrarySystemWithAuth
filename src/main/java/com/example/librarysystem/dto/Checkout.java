package com.example.librarysystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkoutId;
    private Date checkoutDate;
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "bookId", referencedColumnName = "bookId")
    private Book book;


}
