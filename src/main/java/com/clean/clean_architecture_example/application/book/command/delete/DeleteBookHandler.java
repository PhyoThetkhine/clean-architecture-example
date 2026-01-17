package com.clean.clean_architecture_example.application.book.command.delete;

import com.clean.clean_architecture_example.application.BookService;
import org.springframework.stereotype.Component;

@Component
public class DeleteBookHandler {

    private final BookService service;

    public DeleteBookHandler(BookService service) {
        this.service = service;
    }

    public void handle(DeleteBookCommand cmd) {
        service.delete(cmd.getId());
    }
}
