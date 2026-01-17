package com.clean.clean_architecture_example.port;

import com.clean.clean_architecture_example.domain.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Book save(Book book);
    void update(Book book);
    void deleteById(Long id);
}
