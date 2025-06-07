package kg.edu.mathbilim.controller.mvc;


import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.UserDto;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.reference.types.UserTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserTypeService userTypeService;

    @GetMapping("login")
    public String login(@RequestParam(name = "error", required = false) Boolean error,
                        Model model) {
        if (Boolean.TRUE.equals(error)) {
            model.addAttribute("error", true);
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("types", userTypeService.getAllTypes());
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("userDto") @Valid UserDto userDto,
                                      BindingResult result,
                                      Model model) {
        if (result.hasErrors()) {
            model.addAttribute("types", userTypeService.getAllTypes());
            model.addAttribute("errors", result);
            return "auth/register";
        }

        userService.createUser(userDto);
        return "redirect:/login?registered=true";
    }
}
