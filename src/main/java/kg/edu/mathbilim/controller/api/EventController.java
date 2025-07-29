package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.service.interfaces.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("restEvents")
@RequestMapping("api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;



    @PostMapping("{id}/share")
    public ResponseEntity<Void> shareEvent(@PathVariable Long id) {
        eventService.incrementShareCount(id);
        return ResponseEntity.ok().build();
    }
}
