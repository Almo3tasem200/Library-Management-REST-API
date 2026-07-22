package com.example.librarymanagementrestapi.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Component;


@Setter
@Getter
@Component
//Create an instance of this configuration class
// and manage it as a Spring bean,
// so I can inject it anywhere
@ConfigurationProperties(prefix = "library")
public class LibraryConfigProps {

    private String name;
    private int numberOfBooks;

}
