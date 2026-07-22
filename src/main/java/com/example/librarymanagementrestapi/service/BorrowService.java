package com.example.librarymanagementrestapi.service;

import com.example.librarymanagementrestapi.Repository.BookRepository;
import com.example.librarymanagementrestapi.Repository.BorrowRepository;
import com.example.librarymanagementrestapi.model.BookModel;
import com.example.librarymanagementrestapi.model.BorrowRecord;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class BorrowService {

    private final BookRepository bookRepository;
    private final BorrowRepository borrowRepository;
    private final List<Integer> blockedUsers = new ArrayList<>();

    public BorrowService(BookRepository bookRepository, BorrowRepository borrowRepository) {
        this.bookRepository = bookRepository;

        this.borrowRepository = borrowRepository;
    }

    public void borrowBook(int bookId, int userId) {


        // - Check if the user is blocked
        if (blockedUsers.contains(userId)) {
            throw new IllegalArgumentException(
                    "The user is blocked"
            );
        }
        // -Check First if the book is Exist
        BookModel book = bookRepository.findBookById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Book not found with the provided ID.");
        }
        bookOverdue();
        // -Check if the book is available (not currently borrowed)
        if (!book.isAvailable()) {
            throw new IllegalArgumentException("This book is currently unavailable");
        }

        // Check if the user has fewer than 5 active borrowed books
        long borrowedBooks = borrowRepository.getAllBorrowedRecords().stream()
                .filter(bookBorrowed -> bookBorrowed.getUserId() == userId)
                .filter(bookBorrowed -> bookBorrowed.getStatus().equals("Borrowed"))
                .count();
        if (borrowedBooks >= 5) {
            throw new IllegalArgumentException("The user cannot borrow more than five books");
        }
        //Check if the user has 3 or more overdue books; if yes, block the borrow.
        long overdueBooks = borrowRepository.getAllBorrowedRecords().stream()
                .filter(bookBorrowed -> bookBorrowed.getUserId() == userId)
                .filter(bookBorrowed -> bookBorrowed.getStatus().equals("Overdue"))
                .count();
        if (overdueBooks >= 3) {
            blockedUsers.add(userId);
            throw new IllegalArgumentException("The user cannot borrow books while having three overdue books");
        }
        // -Create a new BorrowRecord with current date as borrowDate and calculate
        //dueDate
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setBookId(bookId);
        borrowRecord.setUserId(userId);
        LocalDate today = LocalDate.now();
        borrowRecord.setBorrowDate(today);
        borrowRecord.setStatus("Borrowed"); //Mark the book as "borrowed" (update availability status)
        borrowRecord.setDueDate(today.plusDays(14));
        book.setAvailable(false);
        borrowRepository.addBorrowedBook(borrowRecord);
    }

    public void returnBook(int bookId, int userId) {
        BookModel book = bookRepository.findBookById(bookId);

        if (book == null) {
            throw new IllegalArgumentException("Book with this id not found.");
        }
        BorrowRecord borrowRecord = borrowRepository.getAllBorrowedRecords()
                .stream()
                .filter(bookBorrowed -> bookBorrowed.getBookId() == bookId)
                .filter(bookBorrowed -> bookBorrowed.getUserId() == userId)
                .filter(bookBorrowed -> bookBorrowed.getStatus().equals("Borrowed"))
                .findFirst().orElse(null);
        if (borrowRecord == null) {
            throw new IllegalArgumentException("No active borrow record found.");
        }
        borrowRecord.setReturnDate(LocalDate.now());
        borrowRecord.setStatus("Returned");

        // Make the book available again
        book.setAvailable(true);
    }

    public void bookOverdue() {
        LocalDate today = LocalDate.now();

        borrowRepository.getAllBorrowedRecords()
                .stream()
                .filter(borrowRecord -> borrowRecord.getStatus().equals("Borrowed"))
                .filter(borrowRecord -> today.isAfter(borrowRecord.getDueDate()))
                .forEach(borrowRecord -> borrowRecord.setStatus("Overdue"));
    }

    public boolean isUserBlocked(int userId) {
        return blockedUsers.contains(userId);
    }
}