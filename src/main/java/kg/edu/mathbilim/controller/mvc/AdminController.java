package kg.edu.mathbilim.controller.mvc;

import kg.edu.mathbilim.dto.UserDto;
import kg.edu.mathbilim.enums.UserType;
import kg.edu.mathbilim.service.interfaces.reference.role.RoleService;
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

    @GetMapping
    public String index(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("types", UserType.getAllValues());
        return "admin/admin";
    }
}
