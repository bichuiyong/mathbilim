package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.reference.user_type.UserTypeDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.service.interfaces.reference.role.RoleService;
import kg.edu.mathbilim.service.interfaces.reference.user_type.UserTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.RequestScope;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private final RoleService roleService;
    private final UserTypeService userTypeService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("types", userTypeService.getAllUserTypes());
        return "admin/admin";
    }
    @GetMapping("create-userType")
    public String createUserType(Model model) {
        model.addAttribute("userTypeDto", new UserTypeDto());
        return "admin/createUserType";
    }

    @PostMapping("create-userType")
    public String create(
                         @Valid UserTypeDto userTypeDto,
                         BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/createUserType";
        }
        userTypeService.createUserType(userTypeDto);
        return "redirect:/profile";
    }

    @GetMapping("update-userType")
    public String updateUserType(Model model) {
        model.addAttribute("userTypeDto", new UserTypeDto());
        return "admin/updateUserType";
    }
    @PostMapping("update-userType")
    public String update(@Valid UserTypeDto userTypeDto,
                         @RequestParam int id,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/updateUserType";
        }
        userTypeService.updateUserType(id, userTypeDto);
        return "redirect:/profile";
    }

    @PostMapping("delete")
    public String delete(@RequestParam int id) {
        userTypeService.deleteUserType(id);
        return "redirect:/profile";
    }

}
