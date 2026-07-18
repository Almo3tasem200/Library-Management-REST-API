package com.example.librarymanagementrestapi.cofiguration;

import com.example.librarymanagementrestapi.model.BookModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LibraryConfig {
    @Bean
    public List<BookModel> books() {
        return new ArrayList<>();
    }
}
