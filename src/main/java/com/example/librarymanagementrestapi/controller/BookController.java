package com.example.librarymanagementrestapi.controller;

import com.example.librarymanagementrestapi.model.BookModel;
import com.example.librarymanagementrestapi.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger logger =
            LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService; //bean

    @GetMapping
    public List<BookModel> getAllBooks() {
        logger.info("Fetching all books data");
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookModel findBookById(@PathVariable int id) {
        logger.info("Fetching book data for ID: {}", id);
        return bookService.findBookById(id);
    }

    @PostMapping
    public BookModel addBook(@RequestBody BookModel bookModel) {
        logger.info("Adding a new book");
        return bookService.addBook(bookModel);
    }
    @PutMapping("/{id}")
    public BookModel updateBookModel(@PathVariable int id,@RequestBody BookModel updatedBook){
        logger.info("Updating a book");
        return bookService.updateBookModel(id, updatedBook);
    }
    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable int id){
        logger.info("Deleting a book with ID: {}", id);
        bookService.deleteBookById(id);
    }
}
