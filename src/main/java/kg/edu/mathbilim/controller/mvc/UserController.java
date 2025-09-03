package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.user.ChangePasswordDto;
import kg.edu.mathbilim.dto.user.PublicUserDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.dto.user.UserEditDto;
import kg.edu.mathbilim.service.interfaces.TranslationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.reference.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TranslationService translationService;
    private final RoleService roleService;


    @PreAuthorize("@userSecurity.isOwner(authentication, #id)")
    @GetMapping("{id}")
    public String profile(@PathVariable Long id, Model model) {
        UserDto userDto = userService.getDtoById(id);
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("types", translationService.getUserTypesByLanguage());
        model.addAttribute("user", userDto);
        model.addAttribute("accepted", userService.approvedContentCount(id));
        model.addAttribute("pending", userService.pendingContentCount(id));
        model.addAttribute("total", userService.totalContentCount(id));
        return "profile/profile-page";
    }

    @GetMapping("/{id}/public")
    public String publicProfile(@PathVariable Long id, Principal principal, Model model) {

        if (principal != null) {
            String currentUserEmail = principal.getName();
            UserDto currentUser = userService.getUserByEmail(currentUserEmail);

            if (currentUser != null && currentUser.getId().equals(id)) {
                return "redirect:/users/" + id;
            }
        }

        PublicUserDto userDto = userService.getPublicDtoById(id);
        model.addAttribute("userDto", userDto);
        return "profile/public-profile-page";
    }


    @GetMapping("{id}/edit")
    public String profileEdit(@PathVariable Long id, Model model) {
        if (!model.containsAttribute("userEditDto")) {
            model.addAttribute("userEditDto", userService.getEditUserById(id));
        } else {
            UserEditDto userEditDto = userService.getEditUserById(id);
            model.addAttribute("userEditDto", userEditDto);
        }
        model.addAttribute("types", translationService.getUserTypesByLanguage());
        model.addAttribute("roles", roleService.getAllRoles());
        return "profile/profile-edit";
    }

    @PostMapping("{id}/edit")
    public String profileEdit(@PathVariable Long id,
                              @Valid @ModelAttribute(name = "userEditDto") UserEditDto userEditDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", translationService.getUserTypesByLanguage());
            model.addAttribute("roles", roleService.getAllRoles());
            return "profile/profile-edit";
        }
        userService.edit(userEditDto);

        return "redirect:/users/" + id;
    }

    @PostMapping("{userId}/avatar")
    public String profileAvatar(@PathVariable Long userId,
                                @RequestParam("avatar") MultipartFile file,
                                RedirectAttributes redirectAttributes) {
        userService.setUserAvatar(userId, file);

        redirectAttributes.addFlashAttribute("userEditDto", userService.getEditUserById(userId));
        return "redirect:/users/" + userId + "/edit";
    }

    @GetMapping("change-password")
    public String changePassword(Model model) {
        model.addAttribute("changePasswordDto", new ChangePasswordDto());
        return "profile/change-password";
    }

    @PostMapping("change-password")
    public String changePassword(@Valid @ModelAttribute ChangePasswordDto dto,
                                 BindingResult result,
                                 Model model) {
        if(result.hasErrors()) {
            return "profile/change-password";
        }
        try{
            userService.changePassword(dto.getOldPassword(),dto.getNewPassword());
        }catch (IllegalArgumentException e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("changePasswordDto", dto);
            return "profile/change-password";
        }
        return "redirect:/users/" + userService.getAuthUser().getId();
    }
}