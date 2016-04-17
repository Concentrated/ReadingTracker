package com.example.andy.readingtracker;

/**
 * Created by andy on 2/2/2016.
 */
public class Book {
    private String name;
    private String author;
    private String owner;
    Book(String name, String author, String owner) {
        this.name = name;
        this.author = author;
        this.owner = owner;
    }
    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }
    public String getOwner() {
        return owner;
    }
}
