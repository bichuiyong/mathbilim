package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.test.AttemptAnswerDto;
import kg.edu.mathbilim.dto.test.TestCreateDto;
import kg.edu.mathbilim.dto.test.TestPassDto;
import kg.edu.mathbilim.service.interfaces.test.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller("mvcTest")
@RequestMapping("tests")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping()
    public String tests() {
        return "tests/test-list";
    }

    @GetMapping("{id}")
    public String testDetails(@PathVariable("id") Long id) {
        return "tests/test-details";
    }

    @GetMapping("{id}/pass")
    public String passTest(@PathVariable("id") Long id, Model model) {
        model.addAttribute("test", testService.getTestDtoForPassById(id));
        return "tests/test-pass";
    }

    @GetMapping("attempts/{id}/result")
    public String resultTest(@PathVariable("id") Long attemptId, Model model) {
        model.addAttribute("testResultDto", testService.getResultByAttemptId(attemptId));
        System.out.println("fasfasasf");
        return "tests/test-result";
    }

    @PostMapping("{id}/pass")
    public String passTest(@PathVariable("id") Long id, @ModelAttribute("testPass")TestPassDto testPassDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/tests";
        }

        Long attemptId = testService.passTest(testPassDto,id);
        return "redirect:/tests/attempts/" + attemptId + "/result";
    }

    @GetMapping("create")
    public String createTest(Model model) {
        model.addAttribute("testCreateDto", new TestCreateDto());
        model.addAttribute("topics",testService.getTopics());
        return "tests/test-create";
    }

    @PostMapping("create")
    public String createTestPost(@Valid @ModelAttribute TestCreateDto testCreateDto,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("testCreateDto", testCreateDto);
            return "tests/test-create";
        }
        testService.createTest(testCreateDto);
        return "redirect:/tests";
    }
}
