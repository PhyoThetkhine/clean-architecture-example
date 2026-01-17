package com.clean.clean_architecture_example.application.book.command.delete;

public class DeleteBookCommand {
    private final Long id;

    public DeleteBookCommand(Long id) { this.id = id; }
    public Long getId() { return id; }
}
