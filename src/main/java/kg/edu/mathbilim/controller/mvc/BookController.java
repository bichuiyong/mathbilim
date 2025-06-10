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
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("book", new BookDto());
        return "books/create-book";
    }

    @PostMapping("/create")
    public String addBook(@ModelAttribute("book") @Valid BookDto book,
                          @RequestParam  MultipartFile attachments,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "books/create-book";
        }
        bookService.createBook(book);
        return "redirect:/books/" + book.getId();
    }
}
