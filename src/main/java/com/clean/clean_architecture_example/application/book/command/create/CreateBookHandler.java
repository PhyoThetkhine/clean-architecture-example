package com.clean.clean_architecture_example.application.book.command.create;

import com.clean.clean_architecture_example.domain.Book;
import com.clean.clean_architecture_example.application.BookService;
import org.springframework.stereotype.Component;

@Component
public class CreateBookHandler {

    private final BookService service;

    public CreateBookHandler(BookService service) {
        this.service = service;
    }

    public Book handle(CreateBookCommand cmd) {
        Book b = new Book(cmd.getTitle(), cmd.getAuthor(), cmd.getIsbn());
        return service.create(b);
    }
}
