package com.example.librarymanagementrestapi.model;


import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowRecord {
    private int borrowId ;//(unique identifier)
    private int bookId; //(reference to Book)
    private int userId; //borrower id
    private LocalDate borrowDate; //(date when the book was borrowed)
    private LocalDate dueDate; //(expected return date, e.g., 14 days from borrowDate)
    private LocalDate returnDate; //(actual return date, nullable)
    private String Status; //(e.g., "Borrowed", "Returned", "Overdue")
    private boolean Blocked = false;
}
// if 5 borrowId has 1 userId with status Borrowed