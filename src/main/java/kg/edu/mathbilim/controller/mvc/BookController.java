package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.BookDto;
import kg.edu.mathbilim.service.interfaces.BookService;
import kg.edu.mathbilim.service.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller("mvcBook")
@RequiredArgsConstructor
@RequestMapping("books")
public class BookController {
    private final BookService bookService;
    private final CategoryService categoryService;

    @GetMapping
    public String books(Model model) {
        return "books/book-list";
    }

    @GetMapping("{id}")
    public String book(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", bookService.getById(id));
        return "books/book";
    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("book", new BookDto());
        return "books/create";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid BookDto book,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "books/create";
        }
        return "redirect:/books/" + book.getId();
    }
}
