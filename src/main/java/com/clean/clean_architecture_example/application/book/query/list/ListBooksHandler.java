package com.clean.clean_architecture_example.application.book.query.list;

import com.clean.clean_architecture_example.domain.Book;
import com.clean.clean_architecture_example.dto.BookDto;
import com.clean.clean_architecture_example.port.BookRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListBooksHandler {

    private final BookRepository repository;

    public ListBooksHandler(BookRepository repository) {
        this.repository = repository;
    }

    public List<BookDto> handle(ListBooksQuery q) {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private BookDto toDto(Book b) {
        if (b == null) return null;
        return new BookDto(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn());
    }
}
