package kg.edu.mathbilim.controller.api;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.user.UserTypeDto;
import kg.edu.mathbilim.service.interfaces.reference.UserTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("restUserTypes")
@RequestMapping("api/userTypes")
public class UserTypeController {

    private final UserTypeService userTypeService;

    @PostMapping
    public ResponseEntity<?> createUserType(@RequestBody @Valid UserTypeDto userTypeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userTypeService.createUserType(userTypeDto));
    }

    @PutMapping("{userTypeId}")
    public ResponseEntity<?> updateUserType(@RequestBody @Valid UserTypeDto userTypeDto, @PathVariable Integer userTypeId) {
        return ResponseEntity.ok(userTypeService.updateUserType(userTypeId, userTypeDto));
    }

    @DeleteMapping("{userTypeId}")
    public ResponseEntity<?> deleteUserType(@PathVariable Integer userTypeId) {
        userTypeService.deleteUserType(userTypeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> getUserTypesByQuery(@RequestParam(required = false) String name, @RequestParam(required = false) String lang) {
        return ResponseEntity.ok(userTypeService.getUserTypesByQuery(name, lang));
    }
}
