package com.example.librarymanagementrestapi.service;

import com.example.librarymanagementrestapi.Repository.BookRepository;
import com.example.librarymanagementrestapi.model.BookModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookService bookService;

    @BeforeEach
    void setUp() {
        List<BookModel> books = new ArrayList<>();

        books.add(new BookModel(
                1,
                "Utopia",
                "Ahmed Khaled Tawfik",
                "Dystopian"
        ));

        books.add(new BookModel(
                2,
                "The Blue Elephant",
                "Ahmed Mourad",
                "Psychological Thriller"
        ));
        books.add(new BookModel(
                3,
                "The Days",
                "Taha Hussein",
                "Autobiography"
        ));

        BookRepository bookRepository = new BookRepository(books);
        bookService = new BookService(bookRepository);
    }

    @Test
    void shouldGetAllBooks() {
        List<BookModel> allBooks = bookService.getAllBooks();
        assertEquals(3, allBooks.size());
    }

    @Test
    void shouldFindBookById() {
        BookModel requiredBook = bookService.findBookById(2);
        assertNotNull(requiredBook);
        assertEquals("Ahmed Mourad", requiredBook.getAuthor());
    }
    @Test
    void shouldFindBookByTitle() {
        BookModel requiredBook = bookService.findBookByTitle("The Blue Elephant");
        assertNotNull(requiredBook);
        assertEquals("Ahmed Mourad", requiredBook.getAuthor());
    }

    @Test
    void shouldAddBook() {
        BookModel addedBook = new BookModel(
                4,
                "The Cairo Trilogy",
                "Nagib Mahfouz",
                "Historical Fiction"
        );
        BookModel book = bookService.addBook(addedBook);
        assertEquals(addedBook, book); //check if the added bock equal the returned book
        assertEquals(4, bookService.getAllBooks().size());
        //verify that the book was actually stored in the list.
    }

    @Test
    void ShouldUpdateBook() {
        BookModel updatedBook = new BookModel(
                1,
                "Utopia",
                "Ahmed Khaled Tawfik",
                "Futuristic Sci-Fi"
        );
        BookModel book = bookService.updateBook(1, updatedBook);
        assertEquals("Futuristic Sci-Fi", book.getCategory());
    }

    @Test
    void shouldDeleteBook() {
        bookService.deleteBookById(4);
        assertEquals(3, bookService.getAllBooks().size());
        assertNull(bookService.findBookById(4));
    }
}