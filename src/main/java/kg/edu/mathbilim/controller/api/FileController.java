package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.service.interfaces.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("restFile")
@RequestMapping("api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping
    public ResponseEntity<Page<FileDto>> getFiles(@RequestParam(required = false, defaultValue = "1") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int size,
                                                  @RequestParam(required = false) String query,
                                                  @RequestParam(required = false, defaultValue = "name") String sortBy,
                                                  @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ofNullable(fileService.getFilePage(query, page, size, sortBy, sortDirection));
    }

    @GetMapping("{id}")
    public ResponseEntity<FileDto> getFile(@PathVariable Long id) {
        return ResponseEntity.ofNullable(fileService.getById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        fileService.delete(id);
        return ResponseEntity.ok().build();
    }
}
