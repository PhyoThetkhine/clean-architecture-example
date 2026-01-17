package com.clean.clean_architecture_example.adapter.in.web;

import com.clean.clean_architecture_example.application.BookService;
import com.clean.clean_architecture_example.domain.Book;
import com.clean.clean_architecture_example.dto.BookDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", service.listAll().stream().map(BookMapper::toDto).toList());
        return "books/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("book", new BookDto());
        return "books/form";
    }

    @PostMapping
    public String create(@ModelAttribute BookDto bookDto) {
        Book book = BookMapper.toDomain(bookDto);
        Book created = service.create(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Book book = service.getById(id);
        model.addAttribute("book", BookMapper.toDto(book));
        return "books/form";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BookDto bookDto) {
        Book book = BookMapper.toDomain(bookDto);
        service.update(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/books";
    }
}
