package kg.edu.mathbilim.controller.mvc;

import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import lombok.RequiredArgsConstructor;
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
        model.addAttribute("organization", new OrganizationDto());
        return "organizations/create-organization";
    }

    @PostMapping("create")
    public String create(@ModelAttribute("organization") OrganizationDto organization,
                         @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "organizations/create-organization";
        }
        if (avatarFile != null && avatarFile.isEmpty()) avatarFile = null;

        organizationService.create(organization, avatarFile);
        return "redirect:/organizations";
    }
}
