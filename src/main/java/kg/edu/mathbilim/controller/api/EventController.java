package kg.edu.mathbilim.controller.api;


import kg.edu.mathbilim.dto.event.EventDto;
import kg.edu.mathbilim.service.interfaces.event.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("restEvent")
@RequestMapping("api/event")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Page<EventDto>> getEvents(@RequestParam(required = false) String type,
                                                    @RequestParam(required = false) String sort,
                                                    Pageable pageable) {
        Page<EventDto> allEvent = eventService.getAllEvent(type, sort, pageable);
        log.info(allEvent.getTotalElements() + " events found");
        return ResponseEntity.ok(allEvent);
<<<<<<< HEAD
}
=======

    }
>>>>>>> 8481cb7 (feat: Add API endpoints for news, blog posts, publications, events, and olympiads)

    @PostMapping("{id}/share")
    public ResponseEntity<Void> shareEvent(@PathVariable Long id) {
        eventService.incrementShareCount(id);
        return ResponseEntity.ok().build();
    }
}
