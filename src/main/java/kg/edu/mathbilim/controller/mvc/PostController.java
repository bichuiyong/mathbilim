package kg.edu.mathbilim.controller.mvc;

import kg.edu.mathbilim.dto.PostDto;
import kg.edu.mathbilim.util.PaginationUtil;
import kg.edu.mathbilim.service.interfaces.PostService;
import kg.edu.mathbilim.service.interfaces.PostTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller("mvcPost")
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostTypeService postTypeService;

    @GetMapping("create")
    public String createPost(Model model) {
        model.addAttribute("post", new PostDto());
        model.addAttribute("postTypes", postTypeService.getAllPostTypes());
        return "media/post-create";
    }

    @PostMapping("create")
    public String createPost(@ModelAttribute("post") PostDto post,
                             BindingResult bindingResult,
                             @RequestParam(required = false) MultipartFile[] attachments,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postTypes", postTypeService.getAllPostTypes());
            return "media/post-create";
        }
        if(attachments == null)  attachments = new MultipartFile[0];
        PostDto dto = postService.createPost(post, attachments);
        return "redirect:/posts/" + dto.getType().getName() + "/" + dto.getId();
    }

    @GetMapping("news")
    public String news(Model model) {
        return "media/news-page";
    }

    @GetMapping("publications")
    public String publications(Model model) {
        return "media/publication-page";
    }

    @GetMapping("/{type}")
    public String getAllPosts(Model model) {
        return "media/posts-page";
    }

    @GetMapping("/{type}/{id}")
    public String getFile(@PathVariable String type) {
        return "";
    }

}
