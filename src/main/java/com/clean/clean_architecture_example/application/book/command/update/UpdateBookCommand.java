package com.clean.clean_architecture_example.application.book.command.update;

public class UpdateBookCommand {
    private final Long id;
    private final String title;
    private final String author;
    private final String isbn;

    public UpdateBookCommand(Long id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
}
