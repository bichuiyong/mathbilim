package kg.edu.mathbilim.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("mvcEvent")
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {
    @GetMapping()
    public String eventList() {
        return "events/event-list";
    }

    @GetMapping("event/{id}")
    public String eventDetail(@PathVariable Long id) {
        return "events/event-details";
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
