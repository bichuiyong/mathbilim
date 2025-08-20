package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.service.interfaces.PDFService;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PDFServiceImpl implements PDFService {

    @Override
    public List<MultipartFile> splitPdf(MultipartFile multipartFile) {
        try {
            List<MultipartFile> pages = new ArrayList<>();
            byte[] fileBytes = multipartFile.getBytes();
            try (PDDocument document = Loader.loadPDF(fileBytes)) {
                int totalPages = document.getNumberOfPages();

                for (int i = 0; i < totalPages; i++) {
                    try (PDDocument singlePageDoc = new PDDocument()) {
                        PDPage page = document.getPage(i);
                        singlePageDoc.addPage(page);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        singlePageDoc.save(baos);
                        byte[] pageBytes = baos.toByteArray();


                        MultipartFile pageFile = new MockMultipartFile(
                                "file",
                                "test_" + multipartFile.getOriginalFilename() + "page_" + (i + 1) + ".pdf",
                                "application/pdf",
                                pageBytes
                        );

                        pages.add(pageFile);
                    }
                }
            }
            return pages;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return List.of();
    }
    @Override
    public MultipartFile mergePdfFiles(List<MultipartFile> pdfFiles, String fileName) {
        try {
            PDFMergerUtility merger = new PDFMergerUtility();
            PDDocument mergedDocument = new PDDocument();

            for (MultipartFile multipartFile : pdfFiles) {
                try (PDDocument documentToMerge = Loader.loadPDF(multipartFile.getBytes())) {
                    merger.appendDocument(mergedDocument, documentToMerge);
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            mergedDocument.save(outputStream);
            mergedDocument.close();

            byte[] mergedBytes = outputStream.toByteArray();

            return new MockMultipartFile(
                    "file",
                    fileName,
                    "application/pdf",
                    mergedBytes
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
