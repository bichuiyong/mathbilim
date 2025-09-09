package kg.edu.mathbilim.controller.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.edu.mathbilim.components.SubscriptionModelPopulator;
import kg.edu.mathbilim.dto.event.CreateEventDto;
import kg.edu.mathbilim.dto.event.DisplayEventDto;
import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.Language;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.event.EventService;
import kg.edu.mathbilim.service.interfaces.event.EventTypeService;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import kg.edu.mathbilim.util.UrlUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Locale;

@Controller("mvcEvent")
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventTypeService eventTypeService;
    private final OrganizationService organizationService;
    private final SubscriptionModelPopulator subscriptionModelPopulator;
    private final UserService userService;


    @ModelAttribute
    public void addCommonAttributes(Model model) {
        Locale lan =  LocaleContextHolder.getLocale();
        model.addAttribute("eventsTypes", eventTypeService.getEventTypesByLanguage(lan.getLanguage()));
        model.addAttribute("organizations", organizationService.getOrganizations(null));
        model.addAttribute("languages", Language.getLanguagesMap());
        model.addAttribute("languageEnum", Language.values());
    }

    @GetMapping()
    public String eventList(Authentication auth, Model model) {
        subscriptionModelPopulator.addSubscriptionAttributes(auth, NotificationEnum.EVENT, model);
        return "events/event-list";
    }

    @GetMapping("/{id}")
    public String viewEvent(@PathVariable Long id,
                            HttpServletRequest request,
                            Model model,  Principal principal) {

        String email = (principal != null) ? principal.getName() : null;
        DisplayEventDto event = eventService.getDisplayEventById(id, email);

        model.addAttribute("eventType", eventTypeService.getEventTypeById(event.getTypeId()));

        if (event.getOrganizationIds() != null && !event.getOrganizationIds().isEmpty()) {
            model.addAttribute("organizations",
                    organizationService.getByIds(event.getOrganizationIds()));
        }

        String shareUrl = UrlUtil.getBaseURL(request) + "/events/" + id;

        model.addAttribute("event", event);
        model.addAttribute("shareUrl", shareUrl);
        model.addAttribute("currentUser", principal != null ? userService.getUserByEmail(principal.getName()) : null);

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
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            FieldError attachmentError = bindingResult.getFieldError("attachments");
            if (attachmentError != null) {
                model.addAttribute("attachmentError", attachmentError.getDefaultMessage());
            }

            FieldError mainImageError = bindingResult.getFieldError("image");
            if (mainImageError != null) {
                model.addAttribute("imageError", mainImageError.getDefaultMessage());
            }


            return "events/event-create";
        }
        EventDto eventDto = eventService.create(createEventDto);

        if (eventDto.getStatus() == ContentStatus.APPROVED) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Мероприятие успешно создан и опубликован.");
        } else if (eventDto.getStatus() == ContentStatus.PENDING_REVIEW) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Мероприятие успешно создан и ожидает модерации. После одобрения будет опубликован.");
        }

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

    @PreAuthorize("@eventSecurity.isOwner(#id, principal.username) or hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @PostMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        eventService.delete(id);
        return "redirect:/events";
    }
}