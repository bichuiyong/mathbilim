package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.event.CreateEventDto;
import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.dto.event.EventTranslationDto;
import kg.edu.mathbilim.enums.Language;
import kg.edu.mathbilim.service.interfaces.event.EventService;
import kg.edu.mathbilim.service.interfaces.event.event_type.EventTypeService;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller("mvcEvent")
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventTypeService eventTypeService;
    private final OrganizationService organizationService;
    private final UserService userService;

    @GetMapping()
    public String eventList() {
        return "events/event-list";
    }

    @GetMapping("event/{id}")
    public String eventDetail(@PathVariable Long id, Model model) {
        EventDto event = eventService.getById(id);
        model.addAttribute("event", event);
        model.addAttribute("languageEnum", Language.values());
        return "events/event-details";
    }

    @GetMapping("create")
    public String createEvent(Model model) {
        EventDto eventDto = new EventDto();
        eventDto.setUser(userService.getAuthUser());

        List<EventTranslationDto> translations = new ArrayList<>();
        for (Language lang : Language.values()) {
            translations.add(EventTranslationDto.builder()
                    .languageCode(lang.getCode())
                    .title("")
                    .content("")
                    .build());
        }
        eventDto.setEventTranslations(translations);

        CreateEventDto createEventDto = CreateEventDto.builder()
                .event(eventDto)
                .build();

        model.addAttribute("createEventDto", createEventDto);
        model.addAttribute("eventsTypes", eventTypeService.getEventTypesByLanguage("ru"));
        model.addAttribute("organizations", organizationService.getOrganizations(null));
        model.addAttribute("languages", Language.getLanguagesMap());
        model.addAttribute("languageEnum", Language.values());

        return "events/event-create";
    }

    @PostMapping("create")
    public String createEvent(@ModelAttribute("createEventDto") @Valid CreateEventDto createEventDto,
                              BindingResult bindingResult,
                              Model model) {

        createEventDto.getEvent().setUser(userService.getAuthUser());

        if (bindingResult.hasErrors()) {
            model.addAttribute("eventsTypes", eventTypeService.getEventTypesByLanguage("ru"));
            model.addAttribute("organizations", organizationService.getOrganizations(null));
            model.addAttribute("languages", Language.getLanguagesMap());
            model.addAttribute("languageEnum", Language.values());
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