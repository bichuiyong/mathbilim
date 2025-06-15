package kg.edu.mathbilim.controller.mvc;

import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.service.interfaces.TranslationService;
import kg.edu.mathbilim.service.interfaces.reference.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private final RoleService roleService;
    private final TranslationService translationService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("types", translationService.getUserTypesByLanguage());
        return "admin/admin";
    }
}
