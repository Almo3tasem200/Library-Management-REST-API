package com.example.librarymanagementrestapi.cofiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Component;



@Component
//Create an instance of this configuration class
// and manage it as a Spring bean,
// so I can inject it anywhere
@ConfigurationProperties(prefix = "library")
public class LibraryConfigProps {

    private String name;
    private int numberOfBooks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfBooks() {
        return numberOfBooks;
    }

    public void setNumberOfBooks(int numberOfBooks) {
        this.numberOfBooks = numberOfBooks;
    }
}
