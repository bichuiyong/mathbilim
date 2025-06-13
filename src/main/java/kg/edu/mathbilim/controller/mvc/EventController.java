package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.dto.event.EventTranslationDto;
import kg.edu.mathbilim.enums.Language;
import kg.edu.mathbilim.service.interfaces.event.EventService;
import kg.edu.mathbilim.service.interfaces.reference.event_type.EventTypeService;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        model.addAttribute("user", userService.getAuthUser());

        EventDto eventDto = new EventDto();

        Set<EventTranslationDto> translations = new LinkedHashSet<>();
        for (Language lang : Language.values()) {
            translations.add(EventTranslationDto.builder()
                    .languageCode(lang.getCode())
                    .title("")
                    .content("")
                    .build());
        }
        eventDto.setEventTranslations(translations);

        model.addAttribute("event", eventDto);
        model.addAttribute("eventsTypes", eventTypeService.getEventTypesByLanguage("ru"));
        model.addAttribute("organizations", organizationService.getOrganizations(null));
        model.addAttribute("languages", Language.getLanguagesMap());
        model.addAttribute("languageEnum", Language.values());

        return "events/event-create";
    }

    @PostMapping("create")
    public String createEvent(@ModelAttribute("event") @Valid EventDto event,
                              BindingResult bindingResult,
                              @RequestParam(required = false) MultipartFile mainImageMp,
                              @RequestParam(required = false) MultipartFile[] attachments,
                              @RequestParam(required = false) Long[] organizationIds,
                              Model model) {


        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userService.getAuthUser());
            model.addAttribute("eventsTypes", eventTypeService.getEventTypesByLanguage("ru"));
            model.addAttribute("organizations", organizationService.getOrganizations(null));
            model.addAttribute("languages", Language.getLanguagesMap());
            model.addAttribute("languageEnum", Language.values());
            return "events/event-create";
        }

        if (attachments == null) attachments = new MultipartFile[0];
        if (mainImageMp != null && mainImageMp.isEmpty()) mainImageMp = null;

        List<Long> orgIds = organizationIds != null ? Arrays.asList(organizationIds) : null;

        eventService.create(event, mainImageMp, attachments, orgIds);
        return "redirect:/events";
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