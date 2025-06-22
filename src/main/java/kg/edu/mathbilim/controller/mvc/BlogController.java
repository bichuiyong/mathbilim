package kg.edu.mathbilim.controller.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.dto.blog.DisplayBlogDto;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller("mvcBlog")
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @GetMapping("/create")
    public String createBlogForm(Model model) {
        BlogDto blogDto = BlogDto.builder().build();
        model.addAttribute("blogDto", blogDto);
        return "blog/blog-create";
    }

    @PostMapping("/create")
    public String createBlog(
            @ModelAttribute("blogDto") @Valid BlogDto blogDto,
            BindingResult bindingResult,
            @RequestParam(value = "mpMainImage", required = false) MultipartFile mpMainImage,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("blogDto", blogDto);
            return "blog/blog-create";
        }

        BlogDto createdBlog = blogService.createBlog(blogDto, mpMainImage);
        return "redirect:/blog/" + createdBlog.getId();
    }

    @GetMapping("/{id}")
    public String viewBlog(
            @PathVariable Long id,
            @RequestParam(value = "lang", defaultValue = "ru") String lang,
            HttpServletRequest request,
            Model model) {

        DisplayBlogDto blog = blogService.getDisplayBlogById(id, lang);
        blogService.incrementViewCount(id);
        var relatedBlogs = blogService.getRelatedBlogs(id, lang, 3);

        String baseUrl = request.getScheme() + "://" + request.getServerName() +
                (request.getServerPort() != 80 && request.getServerPort() != 443 ?
                        ":" + request.getServerPort() : "");
        String shareUrl = baseUrl + "/blog/" + id;

        model.addAttribute("blog", blog);
        model.addAttribute("currentLang", lang);
        model.addAttribute("relatedBlogs", relatedBlogs);
        model.addAttribute("shareUrl", shareUrl);

        model.addAttribute("pageTitle", blog.getTitle());
        model.addAttribute("pageDescription", blog.getDescription());
        model.addAttribute("pageImage", blog.getMainImage() != null ?
                "/api/files/" + blog.getMainImage().getId() + "/view" : null);

        return "blog/blog";

    }
}