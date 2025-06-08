package kg.edu.mathbilim.controller.mvc;

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
    public String books() {
        return "books";
    }

    @GetMapping("olymp")
    public String olymp() {
        return "olymp";
    }

    @GetMapping("olymp-details")
    public String olympDetails() {
        return "olymp-details";
    }

    @GetMapping("events")
    public String eventList() {
        return "events/event-list";
    }

    @GetMapping("event/details")
    public String eventDetail() {
        return "events/event-details";
    }

    @GetMapping("test-details")
    public String testDetails() { return "test-details"; }

    @GetMapping("tests")
    public String tests() { return "tests"; }

    @GetMapping("test-ready")
    public String testReady() { return "test-ready"; }

}
