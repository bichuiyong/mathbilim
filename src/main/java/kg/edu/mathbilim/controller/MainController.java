package kg.edu.mathbilim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String index() {
        return "main";
    }

    @GetMapping("books")
    public String books() { return "books"; }

    @GetMapping("olymp")
    public String olymp() { return "olymp"; }

    @GetMapping("olymp-details")
    public String olympDetails() { return "olymp-details"; }
}
