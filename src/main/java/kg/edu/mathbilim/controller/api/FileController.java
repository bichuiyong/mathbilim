package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.model.User;
import kg.edu.mathbilim.service.interfaces.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping()
    public ResponseEntity<FileDto> uploadFile(
            @RequestBody MultipartFile file,
            @RequestParam(value = "context", defaultValue = "general") String context,
            @AuthenticationPrincipal User user) {

        FileDto fileDto = fileService.uploadFile(file, context, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(fileDto);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<FileDto> getFile(@PathVariable Long fileId) {
        return ResponseEntity.ofNullable(fileService.getById(fileId));
    }

    @GetMapping("/my")
    public ResponseEntity<Page<FileDto>> getUserFiles(@AuthenticationPrincipal User user,
                                                      @RequestParam(required = false, defaultValue = "1") int page,
                                                      @RequestParam(required = false, defaultValue = "10") int size,
                                                      @RequestParam(required = false) String query,
                                                      @RequestParam(required = false, defaultValue = "name") String sortBy,
                                                      @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ofNullable(fileService.getUserFiles(user, query, page, size, sortBy, sortDirection));

    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        FileDto fileDto = fileService.getById(fileId);
        byte[] fileContent = fileService.dowloadFile(fileId);

        ByteArrayResource resource = new ByteArrayResource(fileContent);

        String encodedFilename = URLEncoder.encode(fileDto.getFilename(), StandardCharsets.UTF_8)
                .replace("\\+", "%20");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDto.getType().getMimeType()))
                .contentLength(fileContent.length)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileDto.getFilename() + "\"; filename*=UTF-8''" + encodedFilename)
                .body(resource);
    }


    @PutMapping("/{fileId}")
    public ResponseEntity<FileDto> updateFile(
            @PathVariable Long fileId,
            @RequestBody MultipartFile file) {

        FileDto updatedFile = fileService.updateFile(fileId, file);
        return ResponseEntity.ok(updatedFile);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable Long fileId) {

        fileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{fileId}/exists")
    public ResponseEntity<Boolean> checkFileExists(@PathVariable Long fileId) {
        return ResponseEntity.ofNullable(fileService.existsById(fileId));
    }

    @PostMapping("/multiple")
    public ResponseEntity<List<FileDto>> uploadMultipleFiles(
            @RequestBody MultipartFile[] files,
            @RequestParam(value = "context", defaultValue = "general") String context,
            @AuthenticationPrincipal User user) {


        List<FileDto> uploadedFiles = Stream.of(files)
                .map(file -> fileService.uploadFile(file, context, user))
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(uploadedFiles);
    }
}