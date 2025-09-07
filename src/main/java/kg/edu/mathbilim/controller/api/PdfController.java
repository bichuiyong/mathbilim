package kg.edu.mathbilim.controller.api;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@RestController
@RequiredArgsConstructor
public class PdfController {

    @GetMapping("/proxy/pdf")
    public ResponseEntity<byte[]> proxyPdf() {
        try {
            Resource resource = new ClassPathResource("/static/pdf/my.pdf");
            byte[] data = IOUtils.toByteArray(resource.getInputStream());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header("Content-Disposition", "inline; filename=\"my.pdf\"")
                    .body(data);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/proxy/pdfUrl")
    public ResponseEntity<byte[]> proxyPdfUrl() {
        try (InputStream in = new URL("https://kstu.kg/fileadmin/user_upload/proizvodstvennaja_praktika_bst.pdf").openStream()) {
            byte[] data = IOUtils.toByteArray(in);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header("Content-Disposition", "inline; filename=\"proizvodstvennaja_praktika_bst.pdf\"")
                    .body(data);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}