package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/olympiad")
@RequiredArgsConstructor
public class OlympiadController {
    private final OlympiadService olympiadService;
    private final UserService userService;

    @GetMapping()
    public String olympiadPage(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "8") int size,
                               Model model) {
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "olympiad/olymp-list";
    }

    @GetMapping("details")
    public String olympiadPageDetails(@RequestParam long id,
                                      Model model) {
        model.addAttribute("olympiad",olympiadService.getById(id));
        return "olympiad/olymp-details";
    }

    @GetMapping("add")
    public String createOlympiad(Model model,Authentication auth) {
        model.addAttribute("olympiadCreateDto", new OlympiadCreateDto());
        model.addAttribute("user", userService.getUserByEmail(auth.getName()));
        return "olympiad/create-olympiad";
    }


    @PostMapping("add")
    public String createOlympiad(@Valid OlympiadCreateDto olympiadCreateDto, BindingResult result, Model model,
                                 Authentication auth
                                ) {
        model.addAttribute("user", userService.getUserByEmail(auth.getName()));
        if (result.hasErrors()) {
            model.addAttribute("olympiadCreateDto", olympiadCreateDto);

            boolean hasStageFieldErrors = result.getFieldErrors().stream()
                    .anyMatch(error -> error.getField().startsWith("stages["));
            boolean hasOlympiadDateError = result.getGlobalErrors().stream()
                    .anyMatch(error -> "ValidOlympiadDates".equals(error.getCode()));
            if (hasStageFieldErrors) {
                model.addAttribute("dateError", "Пожалуйста, проверьте даты этапов на корректность");
            }
            if (hasOlympiadDateError) {
                model.addAttribute("olympiadDateError", "Дата окончания не может быть раньше даты начала олимпиады");
            }
            if (olympiadCreateDto.getStages().isEmpty()) {
                model.addAttribute("stageError","Добавьте хотя бы один этап");
            }
            return "olympiad/create-olympiad";
        }
        if (olympiadCreateDto.getStages().isEmpty()) {
            model.addAttribute("stageError","Добавьте хотя бы один этап");
            return "olympiad/create-olympiad";
        }
        olympiadService.olympiadCreate(olympiadCreateDto);
        return "redirect:/olympiad";
    }
}
