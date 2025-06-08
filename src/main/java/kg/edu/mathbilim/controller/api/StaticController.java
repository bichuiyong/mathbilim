package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.enums.*;
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

    @GetMapping("tests")
    public ResponseEntity<List<TestStatus>> getTestStatuses() {
        return ResponseEntity.ofNullable(TestStatus.getAllValues());
    }

    @GetMapping("contents")
    public ResponseEntity<List<ContentStatus>> getContentStatuses() {
        return ResponseEntity.ofNullable(ContentStatus.getAllValues());
    }

    @GetMapping("categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ofNullable(Category.getAllValues());
    }

    @GetMapping("users")
    public ResponseEntity<List<UserType>> getAllUserTypes() {
        return ResponseEntity.ofNullable(UserType.getAllValues());
    }

    @GetMapping("events")
    public ResponseEntity<List<EventType>> getAllEventTypes() {
        return ResponseEntity.ofNullable(EventType.getAllValues());
    }

    @GetMapping("files")
    public ResponseEntity<List<FileType>> getAllFileTypes() {
        return ResponseEntity.ofNullable(FileType.getAllPublicTypes());
    }

    @GetMapping("posts")
    public ResponseEntity<List<PostType>> getAllPostTypes() {
        return ResponseEntity.ofNullable(PostType.getAllValues());
    }
}
