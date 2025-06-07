package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.UserTypeDto;
import kg.edu.mathbilim.service.interfaces.UserTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("restUserType")
@RequestMapping("api/user-types")
@RequiredArgsConstructor
public class UserTypeController {
    private final UserTypeService userTypeService;

    @GetMapping
    public ResponseEntity<List<UserTypeDto>> getAllUserTypes() {
        return ResponseEntity.ofNullable(userTypeService.getAllTypes());
    }
}
