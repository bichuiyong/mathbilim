package kg.edu.mathbilim.controller.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.abstracts.DisplayContentDto;
import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import kg.edu.mathbilim.util.UrlUtil;
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
    public String createBlog(@ModelAttribute("blogDto") @Valid BlogDto blogDto,
                             BindingResult bindingResult,
                             @RequestParam(value = "mpMainImage", required = false) MultipartFile mpMainImage,
                             Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("blogDto", blogDto);
            return "blog/blog-create";
        }

        BlogDto createdBlog = blogService.create(blogDto, mpMainImage);
        return "redirect:/blog/" + createdBlog.getId();
    }

    @GetMapping("/{id}")
    public String viewBlog(@PathVariable Long id,
                           HttpServletRequest request,
                           Model model) {

        blogService.incrementViewCount(id);
        DisplayContentDto blog = blogService.getDisplayBlogById(id);

        String shareUrl = UrlUtil.getBaseURL(request) + "/blog/" + id;
        model.addAttribute("blog", blog);
        model.addAttribute("shareUrl", shareUrl);

        return "blog/blog";
    }
}