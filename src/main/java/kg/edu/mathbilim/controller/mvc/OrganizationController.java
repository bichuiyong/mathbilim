package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller("mvcOrganization")
@RequiredArgsConstructor
@RequestMapping("organizations")
public class OrganizationController {
    private final OrganizationService organizationService;
    private final UserService userService;

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("organizationDto", OrganizationDto.builder()
                        .creator(UserDto.builder().build())
                .build());
        return "organizations/create-organization";
    }


    @PostMapping("create")
    public String create(@ModelAttribute("organizationDto") @Valid OrganizationDto organization,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("organizationDto", organization);

            FieldError imageError = bindingResult.getFieldError("avatarFile");

            if (imageError != null) {
                model.addAttribute("imageError", imageError.getDefaultMessage());
            }

            return "organizations/create-organization";
        }

        if (organization.getAvatarFile() != null && organization.getAvatarFile().isEmpty()) {
            organization.setAvatarFile(null);
        }

        organizationService.create(organization, organization.getAvatarFile());

        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PostMapping("delete/{id}")
    public String deleteOrganization(@PathVariable long id) {
        organizationService.delete(id);
        return "redirect:/olympiad";
    }
}
