package com.example.librarymanagementrestapi.controller;

import com.example.librarymanagementrestapi.cofiguration.LibraryConfigProps;
import com.example.librarymanagementrestapi.model.BookModel;
import com.example.librarymanagementrestapi.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(BookController.class)
class BookControllerByWebmvcTest {

    @MockitoBean
    private BookService bookService;
    @MockitoBean
    private LibraryConfigProps props;
    // @MockitoBean: Placed a mocked variant inside
    // slice spring context we get here with the @WebMVC test

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateMockMVC(){
        assertNotNull(mockMvc);
    }

    @Test
    void shouldGetLibraryInfo() throws Exception {
        when(props.getName()).thenReturn("Development Library");
        when(props.getNumberOfBooks()).thenReturn(10);

        mockMvc.perform(get("/books/info")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Development Library Contains 10 books."));

    }

    @Test
    void shouldGetAllBooks() throws Exception {
        List<BookModel> books =List.of(new BookModel(
                1,
                "Utopia",
                "Ahmed Khaled Tawfik",
                "Dystopian"
        ),new BookModel(
                2,
                "The Blue Elephant",
                "Ahmed Mourad",
                "Psychological Thriller"
        ),new BookModel(
                3,
                "The Days",
                "Taha Hussein",
                "Autobiography"
        ));

        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/books")).andDo(print())
                .andExpect(status().isOk());
        verify(bookService).getAllBooks();
    }

    @Test
    void shouldFindBookById() throws Exception{
        List<BookModel> books =List.of(new BookModel(
                1,
                "Utopia",
                "Ahmed Khaled Tawfik",
                "Dystopian"
        ),new BookModel(
                2,
                "The Blue Elephant",
                "Ahmed Mourad",
                "Psychological Thriller"
        ),new BookModel(
                3,
                "The Days",
                "Taha Hussein",
                "Autobiography"
        ));
        BookModel book = books.stream()
                .filter(b->b.getId() == 1)
                .findFirst().orElse(null);
        when(bookService.findBookById(1)).thenReturn(book);

        mockMvc.perform(get("/books/1")).andDo(print())
                .andExpect(status().isOk());
        verify(bookService).findBookById(1);;
    }

    @Test
    void shouldAddBook() throws Exception{
        BookModel book = new BookModel(
                4,
                "The Cairo Trilogy",
                "Nagib Mahfouz",
                "Historical Fiction"
        );
        when(bookService.addBook(book)).thenReturn(book);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "id":4,
                          "title":"The Cairo Trilogy",
                          "author":"Nagib Mahfouz",
                          "category":"Historical Fiction"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isOk());
        verify(bookService).addBook(book);

    }

    @Test
    void shouldUpdateBook() throws Exception{
        BookModel updatedBook = new BookModel(
                1,
                "Utopia",
                "Ahmed Khaled Tawfik",
                "Futuristic Sci-Fi"
        );
        when(bookService.updateBook(1,updatedBook)).thenReturn(updatedBook);

        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "id":1,
                          "title":"Utopia",
                          "author":"Ahmed Khaled Tawfik",
                          "category":"Futuristic Sci-Fi"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isOk());
        verify(bookService).updateBook(1,updatedBook);
    }

    @Test
    void shouldDeleteBookById() throws Exception{
        doNothing().when(bookService).deleteBookById(4);

        mockMvc.perform(delete("/books/4")).andDo(print())
                .andExpect(status().isOk());
        verify(bookService).deleteBookById(4);
    }
}