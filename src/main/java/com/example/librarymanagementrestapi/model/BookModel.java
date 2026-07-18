package com.example.librarymanagementrestapi.model;

import lombok.Data;

@Data
public class BookModel {
    private int id;
    private String title;
    private String author;
    private String category;

    public BookModel(int id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
    }
}
