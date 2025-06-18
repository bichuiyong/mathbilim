package kg.edu.mathbilim.controller.mvc;

import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("mvcBlog")
@RequestMapping("blogs")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

}
