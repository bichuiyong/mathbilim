package kg.edu.mathbilim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("catalog")
@RequiredArgsConstructor
public class CatalogController {

    @GetMapping
    public String main(Model model) {
        return "catalog/catalog";
    }
}
