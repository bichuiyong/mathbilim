package kg.edu.mathbilim.controller.api;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.post.PostTypeDto;
import kg.edu.mathbilim.service.interfaces.post.PostTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/postTypes")
@RestController("restPostTypes")
public class PostTypeController {
    private final PostTypeService postTypeService;



    @PostMapping
    public ResponseEntity<?> createPostType(@RequestBody @Valid PostTypeDto postType) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postTypeService.createPostType(postType));
    }

    @PutMapping
    public ResponseEntity<?> updatePostType(@RequestBody @Valid PostTypeDto postType, Integer postTypeId) {
        return ResponseEntity.ok(postTypeService.updatePostType(postTypeId, postType));
    }

    @DeleteMapping
    public ResponseEntity<?> deletePostType(Integer postTypeId) {
        postTypeService.deletePostType(postTypeId);
        return ResponseEntity.ok().build();
    }
}
