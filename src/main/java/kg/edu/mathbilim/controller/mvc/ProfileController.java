package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.dto.user.UserEditDto;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.reference.user_type.UserTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final UserTypeService userTypeService;


    @GetMapping
    public String profile(Model model, Principal principal) {
        UserDto userDto = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", userDto);
        return "profile/profile-page";
    }


    @GetMapping("edit")
    public String profileEdit(Model model, Principal principal) {
        UserDto userDto = userService.getUserByEmail(principal.getName());
        UserEditDto userEditDto = UserEditDto.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .typeId(userDto.getType().getId())
                .build();
        model.addAttribute("types", userTypeService.getUserTypesByLanguage("ru"));
        model.addAttribute("userEditDto", userEditDto);
        return "profile/profile-edit";
    }

    @PostMapping("edit")
    public String profileEdit(@Valid @ModelAttribute(name = "userEditDto") UserEditDto userEditDto, BindingResult bindingResult, Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userEditDto", userEditDto);
            model.addAttribute("types", userTypeService.getUserTypesByLanguage("ru"));
            model.addAttribute("errors", bindingResult);
            return "profile/profile-edit";
        }
        userService.edit(userEditDto, principal.getName());
        return "redirect:/profile";
    }
}
