package com.example.librarymanagementrestapi.service;

import com.example.librarymanagementrestapi.Repository.BookRepository;
import com.example.librarymanagementrestapi.model.BookModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookModel> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public BookModel findBookById(int id){
        return bookRepository.findBookById(id);
    }


    public BookModel addBook(BookModel book) {
        return bookRepository.addBook(book);
    }

    public BookModel updateBookModel(int id, BookModel updatedBook) {
        return bookRepository.updateBookModel(id, updatedBook);
    }

    public void deleteBookById(int id){
        bookRepository.deleteBookById(id);
    }
}