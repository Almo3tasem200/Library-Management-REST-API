package com.example.librarymanagementrestapi.Repository;

import com.example.librarymanagementrestapi.model.BorrowRecord;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Getter
@Repository
public class BorrowRepository {
    private final List<BorrowRecord> borrowedBooks;

    public BorrowRepository(List<BorrowRecord> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public List<BorrowRecord> getAllBorrowedRecords() {
        return borrowedBooks;
    }
    public void addBorrowedBook(BorrowRecord borrowedBook) {
        borrowedBooks.add(borrowedBook);
    }
    public BorrowRecord findBorrowedBookById(int id) {
        return borrowedBooks.stream()
                .filter(borrowRecord  -> borrowRecord.getBookId() == id)
                .findFirst()
                .orElse(null);
    }
}
