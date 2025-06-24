package kg.edu.mathbilim.controller.mvc;

import kg.edu.mathbilim.dto.olympiad.OlympiadCreateDto;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.service.interfaces.olympiad.OlympiadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/olympiad")
@RequiredArgsConstructor
public class OlympiadController {
    private final OlympiadService olympiadService;

    @GetMapping()
    public String olympiadPage() {
        return "olympiad/olymp-list";
    }

    @GetMapping("new")
    public String createOlympiad(Model model) {
        model.addAttribute("olympiad", new OlympiadCreateDto());
        model.addAttribute("stagesCount", 1);
        return "olympiad/create-olympiad";
    }


    @PostMapping("new")
    public String createOlympiad(OlympiadCreateDto olympiadCreateDto, BindingResult result, Model model, Principal principal) {

        if (result.hasErrors()) {
            model.addAttribute("olympiad", olympiadCreateDto);
            model.addAttribute("stagesCount", olympiadCreateDto.getStages() != null ? olympiadCreateDto.getStages().size() : 1);
            return "olympiad/create-olympiad";
        }
        olympiadService.save(olympiadCreateDto, principal.getName());
        return "redirect:/profile";
    }
}
