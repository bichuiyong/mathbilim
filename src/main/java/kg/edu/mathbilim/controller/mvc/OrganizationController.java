package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller("mvcOrganization")
@RequiredArgsConstructor
@RequestMapping("organizations")
public class OrganizationController {
    private final OrganizationService organizationService;

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
                         @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("organizationDto", organization);
            return "organizations/create-organization";
        }
        if (avatarFile != null && avatarFile.isEmpty()) avatarFile = null;
        organizationService.create(organization, avatarFile);
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PostMapping("delete/{id}")
    public String deleteOrganization(@PathVariable long id) {
        organizationService.delete(id);
        return "redirect:/olympiad";
    }
}
