package com.example.librarysystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String isbn;
    private String title;
    private String author;
    private String description;
    private String location;
    private String category;
    private boolean checkedOut;


//    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "publisherId", referencedColumnName = "publisherId")
    private Publisher publisher;

//    @ManyToOne
//    @JoinColumn(name = "checkedOutBy", referencedColumnName = "memberId")
//    private Member checkedOutBy;

}
