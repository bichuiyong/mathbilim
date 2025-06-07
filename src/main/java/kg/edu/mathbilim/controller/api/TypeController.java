package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.EventTypeDto;
import kg.edu.mathbilim.dto.FileTypeDto;
import kg.edu.mathbilim.dto.PostTypeDto;
import kg.edu.mathbilim.dto.UserTypeDto;
import kg.edu.mathbilim.service.interfaces.EventTypeService;
import kg.edu.mathbilim.service.interfaces.FileTypeService;
import kg.edu.mathbilim.service.interfaces.PostTypeService;
import kg.edu.mathbilim.service.interfaces.UserTypeService;
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
    private final FileTypeService fileTypeService;
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
    public ResponseEntity<List<FileTypeDto>> getAllFileTypes() {
        return ResponseEntity.ofNullable(fileTypeService.getAllTypes());
    }

    @GetMapping("posts")
    public ResponseEntity<List<PostTypeDto>> getAllPostTypes() {
        return ResponseEntity.ofNullable(postTypeService.getAllTypes());
    }
}
