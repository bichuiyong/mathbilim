package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.components.SubscriptionModelPopulator;
import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.dto.interfacePack.OnCreate;
import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.service.interfaces.ContactTypeService;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadService;
import kg.edu.mathbilim.service.interfaces.olympiad.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/olympiad")
@RequiredArgsConstructor
public class OlympiadController {
    private final OlympiadService olympiadService;
    private final UserService userService;
    private final SubscriptionModelPopulator subscriptionModelPopulator;
    private final OrganizationService organizationService;
    private final ContactTypeService contactTypeService;
    private final ResultService resultService;

    @GetMapping()
    public String olympiadPage(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "8") int size,
                               Authentication authentication,
                               Model model) {
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        subscriptionModelPopulator.addSubscriptionAttributes(authentication, NotificationEnum.OLYMPIAD, model);
        return "olympiad/olymp-list";
    }

    @GetMapping("details")
    public String olympiadPageDetails(@RequestParam long id, Model model) {
        model.addAttribute("olympiad", olympiadService.getById(id));
        return "olympiad/olymp-details";
    }

    @GetMapping("create")
    public String createOlympiad(Model model, Authentication auth) {
        model.addAttribute("olympiadCreateDto", new OlympiadCreateDto());
        model.addAttribute("user", userService.getUserByEmail(auth.getName()));
        return "olympiad/create-olympiad";
    }

    @PostMapping("create")
    public String createOlympiad(@Valid @Validated(OnCreate.class) OlympiadCreateDto olympiadCreateDto, BindingResult result, Model model,
                                 Authentication auth) {
        model.addAttribute("user", userService.getUserByEmail(auth.getName()));
        return processOlympiadForm(olympiadCreateDto, result, model, "olympiad/create-olympiad",true);
    }

    @GetMapping("edit")
    public String editOlympiad(@RequestParam long id, Model model) {
        model.addAttribute("olympiadCreateDto", olympiadService.getOlympiadCreateDto(id));
        model.addAttribute("contactTypes", contactTypeService.getTypes());
        model.addAttribute("organizations", organizationService.getAllOrganizationIdNames());
        return "olympiad/edit-olymp";
    }

    @PostMapping("edit")
    public String editOlympiadPost(@Valid @ModelAttribute("olympiadCreateDto") OlympiadCreateDto olympiadCreateDto,
                                   BindingResult result, Model model) {
        model.addAttribute("contactTypes", contactTypeService.getTypes());
        model.addAttribute("organizations", organizationService.getAllOrganizationIdNames());
        return processOlympiadForm(olympiadCreateDto, result, model, "olympiad/edit-olymp",false);
    }

    private String processOlympiadForm(OlympiadCreateDto olympiadCreateDto, BindingResult result, Model model,
                                       String templateName, boolean create) {
        if (result.hasErrors()) {
            model.addAttribute("olympiadCreateDto", olympiadCreateDto);

            boolean hasStageFieldErrors = result.getFieldErrors().stream()
                    .anyMatch(error -> error.getField().startsWith("stages["));
            boolean hasOlympiadDateError = result.getGlobalErrors().stream()
                    .anyMatch(error -> "ValidOlympiadDates".equals(error.getCode()));
            boolean hasStageDateError = result.getFieldErrors().stream()
                    .anyMatch(error -> "stages".equals(error.getField()) && "ValidStageDates".equals(error.getCode()));

            if (hasStageDateError) {
                var stageDateErrors = result.getFieldErrors().stream()
                        .filter(error -> "stages".equals(error.getField()) && "ValidStageDates".equals(error.getCode()))
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                model.addAttribute("dateRangeErrors", stageDateErrors);
            }
            if (hasStageFieldErrors) {
                model.addAttribute("dateError", "Пожалуйста, проверьте даты этапов на корректность");
            }
            if (hasOlympiadDateError) {
                model.addAttribute("olympiadDateError", "Дата окончания не может быть раньше даты начала олимпиады");
            }
            if (olympiadCreateDto.getStages().isEmpty()) {
                model.addAttribute("stageError", "Добавьте хотя бы один этап");
            }
            return templateName;
        }

        if (olympiadCreateDto.getStages().isEmpty()) {
            model.addAttribute("stageError", "Добавьте хотя бы один этап");
            return templateName;
        }

        if (create) {
            olympiadService.olympiadCreate(olympiadCreateDto);
        } else {
            olympiadService.olympiadUpdate(olympiadCreateDto);
        }
        return "redirect:/olympiad";
    }

//    @GetMapping("registration")
//    public String olympiadRegistration(Model model, Authentication auth,
//                                       @RequestParam long stageid) {
//    if ()
//
//    return "olympiad/registration";
//    }

    @PostMapping("add-result")
    public String addStageResult(@RequestParam("stageId") long stageId,
                               @RequestParam("file") MultipartFile file) {
        return resultService.uploadResult(file, stageId);
    }
}