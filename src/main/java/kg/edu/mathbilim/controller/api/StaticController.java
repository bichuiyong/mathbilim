package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.reference.CategoryDto;
import kg.edu.mathbilim.dto.reference.EventTypeDto;
import kg.edu.mathbilim.dto.reference.PostTypeDto;
import kg.edu.mathbilim.enums.*;
import kg.edu.mathbilim.model.reference.UserType;
import kg.edu.mathbilim.service.interfaces.reference.CategoryService;
import kg.edu.mathbilim.service.interfaces.reference.EventTypeService;
import kg.edu.mathbilim.service.interfaces.reference.PostTypeService;
import kg.edu.mathbilim.service.interfaces.reference.UserTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("restTypes")
@RequestMapping("api/dict")
@RequiredArgsConstructor
public class StaticController {
    private final CategoryService categoryService;
    private final EventTypeService eventTypeService;
    private final PostTypeService postService;
    private final UserTypeService userTypeService;

    @GetMapping("tests")
    public ResponseEntity<List<TestStatus>> getTestStatuses() {
        return ResponseEntity.ofNullable(TestStatus.getAllValues());
    }

    @GetMapping("contents")
    public ResponseEntity<List<ContentStatus>> getContentStatuses() {
        return ResponseEntity.ofNullable(ContentStatus.getAllValues());
    }

    @GetMapping("categories")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.ofNullable(categoryService.getAllCategories());
    }

    @GetMapping("users")
    public ResponseEntity<List<UserType>> getAllUserTypes() {
        return ResponseEntity.ofNullable(userTypeService.getAll());
    }

    @GetMapping("events")
    public ResponseEntity<List<EventTypeDto>> getAllEventTypes() {
        return ResponseEntity.ofNullable(eventTypeService.getAllEventTypes());
    }

    @GetMapping("files")
    public ResponseEntity<List<FileType>> getAllFileTypes() {
        return ResponseEntity.ofNullable(FileType.getAllPublicTypes());
    }

    @GetMapping("posts")
    public ResponseEntity<List<PostTypeDto>> getAllPostTypes() {
        return ResponseEntity.ofNullable(postService.getAllPostTypes());
    }
}
