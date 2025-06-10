package kg.edu.mathbilim.controller.mvc;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.EventDto;
import kg.edu.mathbilim.service.interfaces.EventService;
import kg.edu.mathbilim.service.interfaces.reference.EventTypeService;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

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
        return "events/event-details";
    }

    @GetMapping("create")
    public String createEvent(Model model) {
        model.addAttribute("user", userService.getAuthUser());
        model.addAttribute("event", new EventDto());
        model.addAttribute("eventsTypes", eventTypeService.getAllEventTypes());
        model.addAttribute("organizations", organizationService.getOrganizations(null));
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
            model.addAttribute("eventsTypes", eventTypeService.getAllEventTypes());
            model.addAttribute("organizations", organizationService.getOrganizations(null));
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