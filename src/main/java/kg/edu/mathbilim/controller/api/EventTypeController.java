package kg.edu.mathbilim.controller.api;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.event.EventTypeDto;
import kg.edu.mathbilim.service.interfaces.event.EventTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("restEventType")
@RequestMapping("api/eventTypes")
public class EventTypeController {
    private final EventTypeService eventTypeService;

    @PostMapping
    public ResponseEntity<?> createEventType(@RequestBody @Valid EventTypeDto eventTypeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventTypeService.createEventType(eventTypeDto));
    }

    @PutMapping("{eventTypeId}")
    public ResponseEntity<?> updateEventType(@RequestBody @Valid EventTypeDto eventTypeDto, @PathVariable Integer eventTypeId) {
        return ResponseEntity.ok(eventTypeService.updateEventType(eventTypeId, eventTypeDto));
    }

    @DeleteMapping("{eventTypeId}")
    public ResponseEntity<?> deleteEventType(@PathVariable Integer eventTypeId) {
        eventTypeService.deleteEventType(eventTypeId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<?> getAllEventTypesByQuery(@RequestParam(required = false) String name, @RequestParam(required = false) String lang) {
        return ResponseEntity.ok(eventTypeService.getAllEventTypesByQuery(lang, name));
    }
}
