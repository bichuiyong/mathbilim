package kg.edu.mathbilim.controller.mvc;

import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.reference.UserTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserTypeController {
    private final UserService userService;
    private final UserTypeService userTypeService;

    @GetMapping("/select-user-type")
    public String selectUserTypePage(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        String email = authentication.getName();
        var user = userService.getUserByEmail(email);

        if (user != null && user.getType() != null) {
            return "redirect:/";
        }

        var userTypes = userTypeService.getAll();
        model.addAttribute("userTypes", userTypes);
        model.addAttribute("user", user);

        return "auth/select-user-type";
    }

    @PostMapping("/select-user-type")
    public String selectUserType(@RequestParam("userTypeId") Long userTypeId,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        try {
            String email = authentication.getName();
            userService.setUserType(email, userTypeId);

            redirectAttributes.addFlashAttribute("message",
                    "Тип пользователя успешно выбран!");
            return "redirect:/";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Ошибка при выборе типа пользователя");
            return "redirect:/auth/select-user-type";
        }
    }
}
