package com.clean.clean_architecture_example.application.book.query.getById;

import com.clean.clean_architecture_example.domain.Book;
import com.clean.clean_architecture_example.dto.BookDto;
import com.clean.clean_architecture_example.port.BookRepository;
import org.springframework.stereotype.Component;

@Component
public class GetBookByIdHandler {

    private final BookRepository repository;

    public GetBookByIdHandler(BookRepository repository) {
        this.repository = repository;
    }

    public BookDto handle(GetBookByIdQuery q) {
        return repository.findById(q.getId()).map(this::toDto).orElse(null);
    }

    private BookDto toDto(Book b) {
        if (b == null) return null;
        return new BookDto(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn());
    }
}
