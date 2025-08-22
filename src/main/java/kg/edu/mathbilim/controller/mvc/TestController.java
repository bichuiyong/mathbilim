package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.test.TestCreateDto;
import kg.edu.mathbilim.dto.test.TestPassDto;
import kg.edu.mathbilim.dto.test.TestsListDto;
import kg.edu.mathbilim.exception.nsee.TopicNotFoundException;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.test.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller("mvcTest")
@RequestMapping("tests")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;
    private final UserService userService;

    @GetMapping()
    public String tests(Model model,
                        @RequestParam(defaultValue = "") String keyword,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "6") int size,
                        @RequestParam(defaultValue = "createdAt") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,sortBy));
        Page<TestsListDto> tests = testService.getTests(keyword, pageable);
        model.addAttribute("keyword", keyword);
        model.addAttribute("tests", tests);
        model.addAttribute("total", tests.getContent().size());
        return "tests/test-list";
    }

    @GetMapping("{id}")
    public String testDetails(@PathVariable("id") Long id, Model model,Authentication auth) {
        model.addAttribute("test", testService.getTestById(id));
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("lastResults", testService.getLastResultsForUser(auth.getName(), id));
        }
        return "tests/test-details";
    }

    @GetMapping("{id}/pass")
    public String passTest(@PathVariable("id") Long id, Model model, Authentication auth) {
        if (auth == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("test", testService.getTestDtoForPassById(id));
        return "tests/test-pass";
    }

    @GetMapping("attempts/{id}/result")
    public String resultTest(@PathVariable("id") Long attemptId, Model model, Authentication auth) {
        model.addAttribute("result", testService.getResultByAttemptId(attemptId,auth.getName()));
        return "tests/test-pass-info";
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
                                 Model model) throws TopicNotFoundException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("testCreateDto", testCreateDto);
            return "tests/test-create";
        }
        testService.createTest(testCreateDto);
        return "redirect:/tests";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PostMapping("delete/{id}")
    public String deleteTest(@PathVariable long id) {
        testService.deleteTestById(id);
        return "redirect:/olympiad";
    }
}
