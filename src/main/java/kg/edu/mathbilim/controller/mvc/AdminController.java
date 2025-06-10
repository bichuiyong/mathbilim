package kg.edu.mathbilim.controller.mvc;

import kg.edu.mathbilim.dto.UserDto;
import kg.edu.mathbilim.enums.UserType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("types", UserType.getAllValues());
        return "admin/admin";
    }
}
