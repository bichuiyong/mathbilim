package kg.edu.mathbilim.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("mvcTest")
@RequestMapping("tests")
@RequiredArgsConstructor
public class TestController {
    @GetMapping()
    public String tests() {
        return "tests/test-list";
    }

    @GetMapping("{id}")
    public String testDetails(@PathVariable("id") Long id) {
        return "tests/test-details";
    }

    @GetMapping("{id}/pass")
    public String passTest(@PathVariable("id") Long id) {
        return "tests/test-pass";
    }
}
