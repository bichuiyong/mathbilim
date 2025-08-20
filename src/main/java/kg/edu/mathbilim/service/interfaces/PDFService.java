package kg.edu.mathbilim.service.interfaces;

import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface PDFService {
    List<MultipartFile> splitPdf(MultipartFile multipartFile);

    MultipartFile mergePdfFiles(List<MultipartFile> pdfFiles, String fileName);
}
