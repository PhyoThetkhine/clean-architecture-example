package com.clean.clean_architecture_example.application;

import com.clean.clean_architecture_example.domain.AuditLog;
import com.clean.clean_architecture_example.domain.Book;
import com.clean.clean_architecture_example.port.AuditRepository;
import com.clean.clean_architecture_example.port.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository repository;
    private final AuditRepository auditRepository;

    public BookService(BookRepository repository, AuditRepository auditRepository) {
        this.repository = repository;
        this.auditRepository = auditRepository;
    }

    public List<Book> listAll() {
        return repository.findAll();
    }

    public Book getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Book create(Book book) {
        Book created = repository.save(book);
        String details = String.format("title=%s;author=%s;isbn=%s", created.getTitle(), created.getAuthor(), created.getIsbn());
        auditRepository.save(new AuditLog("CREATE", "Book", created.getId(), details));
        return created;
    }

    public void update(Book book) {
        repository.update(book);
        String details = String.format("id=%s;title=%s;author=%s;isbn=%s", book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn());
        auditRepository.save(new AuditLog("UPDATE", "Book", book.getId(), details));
    }

    public void delete(Long id) {
        repository.deleteById(id);
        auditRepository.save(new AuditLog("DELETE", "Book", id, "deleted"));
    }
}
