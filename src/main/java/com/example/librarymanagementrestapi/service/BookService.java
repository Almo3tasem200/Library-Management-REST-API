package com.example.librarymanagementrestapi.service;

import com.example.librarymanagementrestapi.Repository.BookRepository;
import com.example.librarymanagementrestapi.model.BookModel;
import org.springframework.context.annotation.Scope;
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
        if(bookRepository.findBookByTitle(book.getTitle()) != null){
            throw new IllegalArgumentException("This book with title '" + book.getTitle() + "' already exists");
        }
        return bookRepository.addBook(book);
    }

    public BookModel updateBook(int id, BookModel updatedBook) {
        return bookRepository.updateBook(id, updatedBook);
    }


    public void deleteBookById(int id) {
        bookRepository.deleteBookById(id);
    }

    public BookModel findBookByTitle(String title){
        return bookRepository.findBookByTitle(title);
    }
}