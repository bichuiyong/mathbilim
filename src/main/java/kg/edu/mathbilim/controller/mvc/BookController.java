package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.BookDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.service.interfaces.BookService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.TranslationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final FileService fileService;
    private final UserService userService;
    private final TranslationService translationService;

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
        model.addAttribute("categories", translationService.getCategoriesByLanguage());
        model.addAttribute("book", new BookDto());

        return "books/create-book";
    }

    @PostMapping("/create")
    public String addBook(@ModelAttribute("book") @Valid BookDto book,
                          @RequestParam MultipartFile attachments,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", translationService.getCategoriesByLanguage());
            return "books/create-book";
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto user = userService.getUserByEmail(email);

        book.setFile(fileService.uploadFile(attachments, "books"));
        book.setCreator(user);
        bookService.createBook(book);
        return "redirect:/books";
    }

    @PostMapping("delete")
    public String delete(
            @RequestParam Long id,
            Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto user = userService.getUserByEmail(email);
        bookService.delete(id, user);
        return "redirect:/profile";
    }

    @GetMapping("{id}/update")
    public String update(Model model,
                         @PathVariable long id) {
        model.addAttribute("categories", translationService.getCategoriesByLanguage());
        model.addAttribute("book", bookService.getById(id));

        return "books/update-book";
    }

    @PostMapping("{id}/update")
    public String updateBook(@PathVariable long id,
                             @ModelAttribute("book") @Valid BookDto book,
                             @RequestParam MultipartFile attachments,
                             @RequestParam(value = "context", defaultValue = "general") String context,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", translationService.getCategoriesByLanguage());
            return "books/update-book";
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto user = userService.getUserByEmail(email);

        book.setFile(fileService.uploadFile(attachments, context));
        book.setCreator(user);
        book.setId(id);
        bookService.createBook(book);
        return "redirect:/books";
    }
}
