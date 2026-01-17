package com.clean.clean_architecture_example.adapter.out.persistence;

import com.clean.clean_architecture_example.domain.Book;
import com.clean.clean_architecture_example.port.BookRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements BookRepository {

    private final JdbcTemplate jdbc;

    public JdbcBookRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Book> rowMapper = (rs, rowNum) -> new Book(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("isbn")
    );

    @Override
    public List<Book> findAll() {
        return jdbc.query("SELECT id, title, author, isbn FROM books", rowMapper);
    }

    @Override
    public Optional<Book> findById(Long id) {
        var list = jdbc.query("SELECT id, title, author, isbn FROM books WHERE id = ?", rowMapper, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public Book save(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO books (title, author, isbn) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getIsbn());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            book.setId(key.longValue());
        }
        return book;
    }

    @Override
    public void update(Book book) {
        jdbc.update("UPDATE books SET title = ?, author = ?, isbn = ? WHERE id = ?",
                book.getTitle(), book.getAuthor(), book.getIsbn(), book.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("DELETE FROM books WHERE id = ?", id);
    }
}
