package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.event.CreateEventDto;
import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.enums.Language;
import kg.edu.mathbilim.service.interfaces.event.EventService;
import kg.edu.mathbilim.service.interfaces.event.EventTypeService;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller("mvcEvent")
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    private final EventService eventService;
    private final EventTypeService eventTypeService;
    private final OrganizationService organizationService;
    private final RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String secret;

    @ModelAttribute
    public void addCommonAttributes(Model model) {
        model.addAttribute("eventsTypes", eventTypeService.getEventTypesByLanguage("ru"));
        model.addAttribute("organizations", organizationService.getOrganizations(null));
        model.addAttribute("languages", Language.getLanguagesMap());
        model.addAttribute("languageEnum", Language.values());
    }

    @GetMapping()
    public String eventList() {
        return "events/event-list";
    }

    @GetMapping("event/{id}")
    public String eventDetail(@PathVariable Long id, Model model) {
        EventDto event = eventService.getById(id);
        model.addAttribute("event", event);
        return "events/event-details";
    }

    @GetMapping("create")
    public String createEvent(Model model) {
        CreateEventDto createEventDto = CreateEventDto.builder()
                .event(EventDto.builder().build())
                .build();
        model.addAttribute("createEventDto", createEventDto);

        return "events/event-create";
    }

    @PostMapping("create")
    public String createEvent(@ModelAttribute("createEventDto") @Valid CreateEventDto createEventDto,
                              BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "events/event-create";
        }

        eventService.create(createEventDto);
        return "redirect:/events?success=created";
    }

    @GetMapping("olymps")
    public String olymp() {
        return "events/olymps/olymp-list";
    }

    @GetMapping("olymps/{id}")
    public String olympDetails(@PathVariable Long id) {
        return "events/olymps/olymp-details";
    }
}