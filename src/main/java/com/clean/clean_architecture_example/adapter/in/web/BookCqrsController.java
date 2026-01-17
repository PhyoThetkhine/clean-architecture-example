package com.clean.clean_architecture_example.adapter.in.web;

import com.clean.clean_architecture_example.application.book.command.create.CreateBookCommand;
import com.clean.clean_architecture_example.application.book.command.create.CreateBookHandler;
import com.clean.clean_architecture_example.application.book.command.update.UpdateBookCommand;
import com.clean.clean_architecture_example.application.book.command.update.UpdateBookHandler;
import com.clean.clean_architecture_example.application.book.command.delete.DeleteBookCommand;
import com.clean.clean_architecture_example.application.book.command.delete.DeleteBookHandler;
import com.clean.clean_architecture_example.application.book.query.getById.GetBookByIdHandler;
import com.clean.clean_architecture_example.application.book.query.getById.GetBookByIdQuery;
import com.clean.clean_architecture_example.application.book.query.list.ListBooksHandler;
import com.clean.clean_architecture_example.application.book.query.list.ListBooksQuery;
import com.clean.clean_architecture_example.dto.BookDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cqrs/books")
public class BookCqrsController {

    private final ListBooksHandler listHandler;
    private final GetBookByIdHandler getHandler;
    private final CreateBookHandler createHandler;
    private final UpdateBookHandler updateHandler;
    private final DeleteBookHandler deleteHandler;

    public BookCqrsController(ListBooksHandler listHandler, GetBookByIdHandler getHandler,
                              CreateBookHandler createHandler, UpdateBookHandler updateHandler,
                              DeleteBookHandler deleteHandler) {
        this.listHandler = listHandler;
        this.getHandler = getHandler;
        this.createHandler = createHandler;
        this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", listHandler.handle(new ListBooksQuery()));
        return "books/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("book", new BookDto());
        return "books/form";
    }

    @PostMapping
    public String create(@ModelAttribute BookDto dto) {
        createHandler.handle(new CreateBookCommand(dto.getTitle(), dto.getAuthor(), dto.getIsbn()));
        return "redirect:/cqrs/books";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        BookDto b = getHandler.handle(new GetBookByIdQuery(id));
        model.addAttribute("book", b);
        return "books/form";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BookDto dto) {
        updateHandler.handle(new UpdateBookCommand(dto.getId(), dto.getTitle(), dto.getAuthor(), dto.getIsbn()));
        return "redirect:/cqrs/books";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        deleteHandler.handle(new DeleteBookCommand(id));
        return "redirect:/cqrs/books";
    }
}
