package com.clean.clean_architecture_example.adapter.in.web;

import com.clean.clean_architecture_example.domain.Book;
import com.clean.clean_architecture_example.dto.BookDto;

public final class BookMapper {

    private BookMapper() {}

    public static BookDto toDto(Book b) {
        if (b == null) return null;
        return new BookDto(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn());
    }

    public static Book toDomain(BookDto d) {
        if (d == null) return null;
        Book b = new Book();
        b.setId(d.getId());
        b.setTitle(d.getTitle());
        b.setAuthor(d.getAuthor());
        b.setIsbn(d.getIsbn());
        return b;
    }
}
