package com.example.librarymanagementrestapi.Repository;

import com.example.librarymanagementrestapi.model.BookModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    private final List<BookModel> books;

    public BookRepository(List<BookModel> books) {
        this.books = books;
    }

    public List<BookModel> getAllBooks(){
        return books;
    }

    public BookModel findBookById(int id) {
        for(BookModel book: books) {
            if(book.getId() == id){
                return books.get(id);
            }
        }
        return null;
    }

    public BookModel addBook(BookModel book) {
        books.add(book);
        return book;
    }

    public BookModel updateBookModel(int id, BookModel updatedBook) {
        BookModel book = findBookById(id);
        if (book != null) {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setCategory(updatedBook.getCategory());
        }
        return book;
    }

    public void deleteBookById(int Id){
        BookModel book = findBookById(Id);
        if (book != null) {
            books.remove(book);
        }
    }
}
