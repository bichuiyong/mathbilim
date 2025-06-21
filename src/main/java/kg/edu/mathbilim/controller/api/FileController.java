package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.service.interfaces.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping()
    public ResponseEntity<FileDto> uploadFile(
            @RequestBody MultipartFile file,
            @RequestParam(value = "context", defaultValue = "general") String context) {

        FileDto fileDto = fileService.uploadFile(file, context);
        return ResponseEntity.status(HttpStatus.CREATED).body(fileDto);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<FileDto> getFile(@PathVariable Long fileId) {
        return ResponseEntity.ofNullable(fileService.getById(fileId));
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        FileDto fileDto = fileService.getById(fileId);
        byte[] fileContent = fileService.downloadFile(fileId);

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

    @GetMapping("/{fileId}/view")
    public ResponseEntity<Resource> viewFile(@PathVariable Long fileId) {
        FileDto fileDto = fileService.getById(fileId);
        byte[] fileContent = fileService.downloadFile(fileId);

        ByteArrayResource resource = new ByteArrayResource(fileContent);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDto.getType().getMimeType()))
                .contentLength(fileContent.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=31536000")
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
            @RequestParam(value = "context", defaultValue = "general") String context) {


        List<FileDto> uploadedFiles = Stream.of(files)
                .map(file -> fileService.uploadFile(file, context))
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(uploadedFiles);
    }

    @PostMapping("/tinymce/image")
    public ResponseEntity<Map<String, String>> uploadImageForTinyMCE(
            @RequestParam("file") MultipartFile file) {

        if (file.getSize() > 5 * 1024 * 1024) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Размер изображения не должен превышать 5MB"));
        }

        try {
            FileDto fileDto = fileService.uploadFile(file, "blog/images");
            String viewUrl = "/api/files/" + fileDto.getId() + "/view";
            Map<String, String> response = Map.of("location", viewUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Ошибка загрузки изображения"));
        }
    }

    @PostMapping("/tinymce/video")
    public ResponseEntity<Map<String, String>> uploadVideoForTinyMCE(
            @RequestParam("file") MultipartFile file) {

        if (file.getSize() > 100 * 1024 * 1024) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Размер видео не должен превышать 100MB"));
        }

        try {
            FileDto fileDto = fileService.uploadFile(file, "blog/videos");
            String viewUrl = "/api/files/" + fileDto.getId() + "/view";
            Map<String, String> response = Map.of("location", viewUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Ошибка загрузки видео"));
        }
    }
}