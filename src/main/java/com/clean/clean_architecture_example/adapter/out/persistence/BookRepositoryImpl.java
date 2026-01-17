package com.clean.clean_architecture_example.adapter.out.persistence;

import com.clean.clean_architecture_example.domain.Book;
import com.clean.clean_architecture_example.port.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Repository
public class BookRepositoryImpl implements BookRepository {

    private final BookSpringDataRepository repo;

    public BookRepositoryImpl(BookSpringDataRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Book> findAll() {
        List<Book> out = new ArrayList<>();
        repo.findAll().forEach(e -> out.add(toDomain(e)));
        return out;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return repo.findById(id).map(this::toDomain);
    }

    @Override
    public Book save(Book book) {
        BookEntity entity = new BookEntity(null, book.getTitle(), book.getAuthor(), book.getIsbn());
        BookEntity saved = repo.save(entity);
        return toDomain(saved);
    }

    @Override
    public void update(Book book) {
        repo.findById(book.getId()).ifPresent(e -> {
            e.setTitle(book.getTitle());
            e.setAuthor(book.getAuthor());
            e.setIsbn(book.getIsbn());
            repo.save(e);
        });
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    private Book toDomain(BookEntity e) {
        if (e == null) return null;
        return new Book(e.getId(), e.getTitle(), e.getAuthor(), e.getIsbn());
    }
}
