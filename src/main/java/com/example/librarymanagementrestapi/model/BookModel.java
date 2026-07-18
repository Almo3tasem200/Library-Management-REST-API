package com.example.librarymanagementrestapi.model;

import lombok.Data;

@Data
public class BookModel {
    private int id;
    private String title;
    private String author;
    private String category;
}
