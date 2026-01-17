package com.clean.clean_architecture_example.application.book.query.getById;

public class GetBookByIdQuery {
    private final Long id;

    public GetBookByIdQuery(Long id) { this.id = id; }
    public Long getId() { return id; }
}
