package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.ContactTypeDto;
import kg.edu.mathbilim.service.interfaces.ContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/contactType")
public class ContactTypeController {
    private final ContactTypeService contactTypeService;

    @GetMapping
    public ResponseEntity<List<ContactTypeDto>> getContactTypes() {
        return ResponseEntity.status(HttpStatus.OK).body(contactTypeService.getTypes());
    }
}
