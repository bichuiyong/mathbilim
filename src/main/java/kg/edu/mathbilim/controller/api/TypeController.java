package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.reference.types.EventTypeDto;
import kg.edu.mathbilim.dto.reference.types.PostTypeDto;
import kg.edu.mathbilim.dto.reference.types.UserTypeDto;
import kg.edu.mathbilim.enums.FileType;
import kg.edu.mathbilim.service.interfaces.reference.types.EventTypeService;
import kg.edu.mathbilim.service.interfaces.reference.types.PostTypeService;
import kg.edu.mathbilim.service.interfaces.reference.types.UserTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("restTypes")
@RequestMapping("api/types")
@RequiredArgsConstructor
public class TypeController {
    private final UserTypeService userTypeService;
    private final EventTypeService eventTypeService;
    private final PostTypeService postTypeService;

    @GetMapping("users")
    public ResponseEntity<List<UserTypeDto>> getAllUserTypes() {
        return ResponseEntity.ofNullable(userTypeService.getAllTypes());
    }

    @GetMapping("events")
    public ResponseEntity<List<EventTypeDto>> getAllEventTypes() {
        return ResponseEntity.ofNullable(eventTypeService.getAllTypes());
    }

    @GetMapping("files")
    public ResponseEntity<List<FileType>> getAllFileTypes() {
        return ResponseEntity.ofNullable(FileType.getAllPublicTypes());
    }

    @GetMapping("posts")
    public ResponseEntity<List<PostTypeDto>> getAllPostTypes() {
        return ResponseEntity.ofNullable(postTypeService.getAllTypes());
    }
}
