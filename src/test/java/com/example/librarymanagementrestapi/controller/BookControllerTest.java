package com.example.librarymanagementrestapi.controller;


import com.example.librarymanagementrestapi.configuration.LibraryConfigProps;
import com.example.librarymanagementrestapi.model.BookModel;
import com.example.librarymanagementrestapi.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @InjectMocks
// Creates a real instance of the class under test
// and injects the mocked dependencies into it
    private BookController bookController;
    //The fake objects are created by @Mock.
    @Mock
    private LibraryConfigProps props;
    @Mock
    private BookService bookService;


    @Test
    void shouldGetLibraryInfo() {
        when(props.getName()).thenReturn("Development Library");
        when(props.getNumberOfBooks()).thenReturn(10);

        String libraryInfo = bookController.getLibraryInfo();

        assertEquals("Development Library Contains 10 books.", libraryInfo);
    }

    @Test
    void shouldGetAllBooks() {
        List<BookModel> books = List.of(new BookModel(
                1,
                "Utopia",
                "Ahmed Khaled Tawfik",
                "Dystopian"
        ), new BookModel(
                2,
                "The Blue Elephant",
                "Ahmed Mourad",
                "Psychological Thriller"
        ), new BookModel(
                3,
                "The Days",
                "Taha Hussein",
                "Autobiography"
        ));
        when(bookService.getAllBooks()).thenReturn(books);

        List<BookModel> allBooks = bookController.getAllBooks();
        assertEquals(books, allBooks);
    }

    @Test
    void shouldFindBookById() {
        List<BookModel> books = List.of(new BookModel(
                1,
                "Utopia",
                "Ahmed Khaled Tawfik",
                "Dystopian"
        ), new BookModel(
                2,
                "The Blue Elephant",
                "Ahmed Mourad",
                "Psychological Thriller"
        ), new BookModel(
                3,
                "The Days",
                "Taha Hussein",
                "Autobiography"
        ));
        BookModel book = books.stream()
                .filter(b -> b.getId() == 1)
                .findFirst().orElse(null);
        when(bookService.findBookById(1)).thenReturn(book);
        BookModel bookFound = bookController.findBookById(1);
        assertEquals(book, bookFound);
        verify(bookService).findBookById(1);
    }
    @Test
    void shouldFindBookByTitle() {
        List<BookModel> books = List.of(new BookModel(
                1,
                "Utopia",
                "Ahmed Khaled Tawfik",
                "Dystopian"
        ), new BookModel(
                2,
                "The Blue Elephant",
                "Ahmed Mourad",
                "Psychological Thriller"
        ), new BookModel(
                3,
                "The Days",
                "Taha Hussein",
                "Autobiography"
        ));
        BookModel book = books.stream()
                .filter(b -> b.getTitle().equals("Utopia"))
                .findFirst().orElse(null);
        when(bookService.findBookByTitle("Utopia")).thenReturn(book);
        BookModel bookFound = bookController.findBookByTitle("Utopia");
        assertEquals(book, bookFound);
        verify(bookService).findBookByTitle("Utopia");
    }

    @Test
    void shouldAddBook() {
        BookModel book = new BookModel(
                4,
                "The Cairo Trilogy",
                "Nagib Mahfouz",
                "Historical Fiction"
        );
        when(bookService.addBook(book)).thenReturn(book);
        BookModel bookAdded = bookController.addBook(book);
        assertEquals(book, bookAdded);
        verify(bookService).addBook(book);
    }

    @Test
    void shouldUpdateBook() {
        BookModel updatedBook = new BookModel(
                1,
                "Utopia",
                "Ahmed Khaled Tawfik",
                "Futuristic Sci-Fi"
        );
        when(bookService.updateBook(1, updatedBook)).thenReturn(updatedBook);
        BookModel bookUpdated = bookController.updateBook(1, updatedBook);
        assertEquals(updatedBook, bookUpdated);
        verify(bookService).updateBook(1, updatedBook);
    }

    @Test
    void shouldDeleteBookById() {
        bookController.deleteBookById(4);

        verify(bookService).deleteBookById(4);
    }
}