package com.clean.clean_architecture_example.application.book.command.update;

import com.clean.clean_architecture_example.domain.Book;
import com.clean.clean_architecture_example.application.BookService;
import org.springframework.stereotype.Component;

@Component
public class UpdateBookHandler {

    private final BookService service;

    public UpdateBookHandler(BookService service) {
        this.service = service;
    }

    public void handle(UpdateBookCommand cmd) {
        Book b = new Book(cmd.getId(), cmd.getTitle(), cmd.getAuthor(), cmd.getIsbn());
        service.update(b);
    }
}
