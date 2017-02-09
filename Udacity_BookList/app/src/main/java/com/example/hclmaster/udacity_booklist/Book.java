package com.example.hclmaster.udacity_booklist;

/**
 * Created by hclmaster on 2017/2/8.
 */

public class Book {
    private String title;
    private String authors;
    private String publishedDate;
    private String description;

    public Book(String title, String authors, String publishedDate, String description) {
        this.title = title;
        this.authors = authors;
        this.publishedDate = publishedDate;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getDescription() {
        return description;
    }
}
