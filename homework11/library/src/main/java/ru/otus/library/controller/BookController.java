package ru.otus.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {
    @GetMapping("/")
    public String mainPage() {
        return "redirect:/book";
    }

    @GetMapping("/book")
    public String getAllBooks(Model model) {
        return "books";
    }
}
