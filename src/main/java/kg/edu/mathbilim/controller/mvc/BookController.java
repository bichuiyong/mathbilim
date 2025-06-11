package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.BookDto;
import kg.edu.mathbilim.dto.UserDto;
import kg.edu.mathbilim.enums.Metadata;
import kg.edu.mathbilim.mapper.UserMapper;
import kg.edu.mathbilim.model.User;
import kg.edu.mathbilim.service.interfaces.BookService;
import kg.edu.mathbilim.service.interfaces.CategoryService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.reference.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller("mvcBook")
@RequiredArgsConstructor
@RequestMapping("books")
public class BookController {
    private final BookService bookService;
    private final CategoryService categoryService;
    private final FileService fileService;
    private final UserMapper userMapper;
    private final UserService userService;

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
        model.addAttribute("metadataKeysEnum", Metadata.values());

        return "books/create-book";
    }

    @PostMapping("/create")
    public String addBook(@ModelAttribute("book") @Valid BookDto book,
                          @RequestParam MultipartFile attachments,
                          @RequestParam(value = "context", defaultValue = "general") String context,
                          @RequestParam("metadataKeys") List<String> keys,
                          @RequestParam("metadataValues") List<String> values,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "books/create-book";
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto user = userService.getUserByEmail(email);

        book.setFile(fileService.uploadFile(attachments, context, userMapper.toEntity(user)));
        book.setUser(user);
        bookService.createBook(book, keys, values);
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

    @GetMapping("/update")
    public String update(Model model,
                         @RequestParam long id) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("book", bookService.getById(id));
        model.addAttribute("metadataKeysEnum", Metadata.values());

        return "books/update-book";
    }

    @PostMapping("/update")
    public String updateBoook(@RequestParam long id,
            @ModelAttribute("book") @Valid BookDto book,
                              @RequestParam MultipartFile attachments,
                              @RequestParam(value = "context", defaultValue = "general") String context,
                              @RequestParam("metadataKeys") List<String> keys,
                              @RequestParam("metadataValues") List<String> values,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "books/update-book";
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto user = userService.getUserByEmail(email);

        book.setFile(fileService.uploadFile(attachments, context, userMapper.toEntity(user)));
        book.setUser(user);
        book.setId(id);
        bookService.createBook(book, keys, values);
        return "redirect:/books";


    }
}
