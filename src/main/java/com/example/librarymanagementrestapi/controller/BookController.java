package com.example.librarymanagementrestapi.controller;

import com.example.librarymanagementrestapi.configuration.LibraryConfigProps;
import com.example.librarymanagementrestapi.model.BookModel;
import com.example.librarymanagementrestapi.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/books")
public class BookController {

    private final LibraryConfigProps props;

    private static final Logger logger =
            LoggerFactory.getLogger(BookController.class);

    public BookController(LibraryConfigProps props, BookService bookService) {
        this.props = props;
        this.bookService = bookService;
    }

    private final BookService bookService; //bean

    @GetMapping("/info")
    public String getLibraryInfo() {
        return props.getName() + " Contains "
                + props.getNumberOfBooks() + " books.";
    }

    @GetMapping
    public List<BookModel> getAllBooks() {
        logger.info("Fetching all books data");
        return bookService.getAllBooks();
    }

    @GetMapping("id/{id}")
    public BookModel findBookById(@PathVariable int id) {
        logger.info("Fetching book data for ID: {}", id);
        return bookService.findBookById(id);
    }

    @GetMapping("/title/{title}")
    public BookModel findBookByTitle(@PathVariable String title) {
        logger.info("Fetching book data for title: {}", title);
        return bookService.findBookByTitle(title);
    }

    @PostMapping
    public BookModel addBook(@RequestBody BookModel bookModel) {
        logger.info("Adding a new book");
        return bookService.addBook(bookModel);
    }

    @PutMapping("/{id}")
    public BookModel updateBook(@PathVariable int id, @RequestBody BookModel updatedBook) {
        logger.info("Updating a book");
        return bookService.updateBook(id, updatedBook);
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable int id) {
        logger.info("Deleting a book with ID: {}", id);
        bookService.deleteBookById(id);
    }
}
