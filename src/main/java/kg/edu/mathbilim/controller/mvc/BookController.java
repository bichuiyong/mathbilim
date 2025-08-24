package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.book.BookDto;
import kg.edu.mathbilim.service.interfaces.BookService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.TranslationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Optional;

@Controller("mvcBook")
@RequiredArgsConstructor
@RequestMapping("books")
public class BookController {
    private final BookService bookService;
    private final FileService fileService;
    private final TranslationService translationService;
    private final UserService userService;

    @GetMapping
    public String books(@RequestParam(required = false) String query,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "size", defaultValue = "5") int size,
                        @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                        @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
                        @RequestParam(required = false) Long categoryId,
                        Model model) {

        int safePage = Math.max(1, page);
        model.addAttribute("book", bookService.getAllBooks("APPROVED",
                query,
                safePage,
                size,
                sortBy,
                sortDirection,
                categoryId));
        model.addAttribute("query", query);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("categories", translationService.getCategoriesByLanguage());
        model.addAttribute("categoryId", categoryId);
        return "books/book-list";
    }

    @GetMapping("{id}")
    public String book(@PathVariable("id") Long id, Model model, Principal principal) {
        String email = Optional.ofNullable(principal)
                .map(Principal::getName)
                .orElse(null);

        model.addAttribute("currentUser", email != null ? userService.getUserByEmail(email) : null);        model.addAttribute("book", bookService.getById(id));
        return "books/book-detail";
    }

    @GetMapping("/create")
    public String create(Model model, Authentication auth) {
        if (auth == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("categories", translationService.getCategoriesByLanguage());
        model.addAttribute("book",new BookDto());

        return "books/create-book";
    }

    @PostMapping("/create")
    public String addBook(@ModelAttribute("book") @Valid BookDto book,
//                          @RequestParam MultipartFile attachments,
                          BindingResult bindingResult,
//                          @RequestParam(value = "mpMainImage", required = false) MultipartFile mpMainImage,
                          Model model
                         ) {
        if (bindingResult.hasErrors()) {
            FieldError attachmentError = bindingResult.getFieldError("attachments");
            if (attachmentError != null) {
                model.addAttribute("attachmentError", attachmentError.getDefaultMessage());
            }
            model.addAttribute("categories", translationService.getCategoriesByLanguage());
            return "books/create-book";
        }

        bookService.createBook(book.getAttachments(), book.getMpMainImage(), book);
        return "redirect:/books";
    }

    @PostMapping("delete")
    public String delete(@RequestParam Long id) {
        bookService.delete(id);
        return "redirect:/profile";
    }

    @GetMapping("update/{id}")
    public String update(Model model,
                         @PathVariable long id) {
        BookDto book =  bookService.getById(id);
        model.addAttribute("categories", translationService.getCategoriesByLanguage());
        model.addAttribute("book", book);
        if (book.getFile() != null) {
            FileDto existingFile = fileService.getById(book.getFile().getId());
            model.addAttribute("existingFile", existingFile);
        }
        return "books/update-book";
    }

    @PostMapping("update/{id}")
    public String updateBook(@PathVariable long id,
                             @ModelAttribute("book") @Valid BookDto book,
                             @RequestParam MultipartFile attachments,
                             @RequestParam(value = "mpMainImage", required = false) MultipartFile mpMainImage,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", translationService.getCategoriesByLanguage());
            BookDto existingBook = bookService.getById(id);
            if (existingBook.getFile() != null) {
                FileDto existingFile = fileService.getById(existingBook.getFile().getId());
                model.addAttribute("existingFile", existingFile);
            }
        }
        bookService.updateBook(id,attachments,book, mpMainImage);
        return "redirect:/books";
    }

    @GetMapping("/{fileId}/pdf-reader")
    public String pdfReader(@PathVariable Long fileId, Model model) {
        FileDto fileDto = fileService.getById(fileId);

        if (fileDto == null) {
            return "redirect:/books?error=file_not_found";
        }

        if (!"application/pdf".equals(fileDto.getType().getMimeType())) {
            return "redirect:/books?error=not_pdf";
        }

        model.addAttribute("fileDto", fileDto);
        model.addAttribute("pdfUrl", "/api/files/" + fileId + "/view");

        return "books/pdf-reader";
    }
}
