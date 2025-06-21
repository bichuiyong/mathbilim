package kg.edu.mathbilim.controller.advice;

import kg.edu.mathbilim.controller.mvc.BlogController;
import kg.edu.mathbilim.controller.mvc.NewsController;
import kg.edu.mathbilim.controller.mvc.PostController;
import kg.edu.mathbilim.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes = {PostController.class, NewsController.class, BlogController.class})
@RequiredArgsConstructor
public class GlobalControllerAdvice {


    @ModelAttribute
    public void addCommonAttributes(Model model) {
        model.addAttribute("languages", Language.getLanguagesMap());
        model.addAttribute("languageEnum", Language.values());
    }
}
