package com.example.librarymanagementrestapi.service;

import com.example.librarymanagementrestapi.Repository.BookRepository;
import com.example.librarymanagementrestapi.model.BookModel;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookModel> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public BookModel findBookById(int id) {
        return bookRepository.findBookById(id);
    }


    public BookModel addBook(BookModel book) {
        return bookRepository.addBook(book);
    }

    public BookModel updateBook(int id, BookModel updatedBook) {
        return bookRepository.updateBook(id, updatedBook);
    }

    public void deleteBookById(int id) {
        bookRepository.deleteBookById(id);
    }
}