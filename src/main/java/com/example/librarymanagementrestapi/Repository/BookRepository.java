package com.example.librarymanagementrestapi.Repository;

import com.example.librarymanagementrestapi.model.BookModel;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository {
    private final List<BookModel> books;
    public BookRepository(List<BookModel> books) {
        this.books = books;
    }

    public List<BookModel> getAllBooks() {
        return books;
    }

    public BookModel findBookById(int id) {
        for (BookModel book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }
    public BookModel findBookByTitle(String title){
        return books.stream()
                .filter(book -> book.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }


    public BookModel addBook(BookModel book) {
        books.add(book);
        return book;
    }

    public BookModel updateBook(int id, BookModel updatedBook) {
        BookModel book = findBookById(id);
        if (book != null) {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setCategory(updatedBook.getCategory());
        }
        return book;
    }

    public void deleteBookById(int id) {
        BookModel book = findBookById(id);

        if (book == null) {
            // If the book doesn't exist, do nothing (deleting a non-existing id is a no-op)
            return;
        }

        if (!book.isAvailable()) {
            throw new IllegalArgumentException("This book with title '" + book.getTitle() + "' is currently borrowed");
        }

        books.remove(book);
    }
}
