package kg.edu.mathbilim.controller.mvc;

import kg.edu.mathbilim.telegram.service.TelegramNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    @GetMapping
    public String index() {
        return "main";
    }

    @GetMapping("/privacy")
    public String privacy() {
        return "/mainPages/privacy";
    }

    @GetMapping("/terms")
    public String terms() {
        return "";
    }

    @GetMapping("/help")
    public String help() {
        return "";
    }

    @GetMapping("about")
    public String about() {
        return "about";
    }
}
