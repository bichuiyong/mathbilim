package kg.edu.mathbilim.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import kg.edu.mathbilim.dto.olympiad.RegistrationDto;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelService {

    public void generateExcelFile(List<RegistrationDto> registrations, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Registrations");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"ФИО", "WebsiteEmail", "Email","Область","Регион","Номер телефона"
                ,"Школа","Класс","Населенный пункт","Руководительница","ФИО родителя"
                ,"Номер родителя","Почта родителя","Олимпиада","Телеграм"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (RegistrationDto regis : registrations) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(regis.getFullName());
            row.createCell(1).setCellValue(regis.getUserName());
            row.createCell(2).setCellValue(regis.getEmail());
            row.createCell(3).setCellValue(regis.getRegion());
            row.createCell(4).setCellValue(regis.getDistrict());
            row.createCell(5).setCellValue(regis.getPhoneNumber());
            row.createCell(6).setCellValue(regis.getSchool());
            row.createCell(7).setCellValue(regis.getClassNumber());
            row.createCell(8).setCellValue(regis.getLocality());
            row.createCell(9).setCellValue(regis.getClassTeacherFullName());
            row.createCell(10).setCellValue(regis.getParentFullName());
            row.createCell(11).setCellValue(regis.getParentPhoneNumber());
            row.createCell(12).setCellValue(regis.getParentEmail());
            row.createCell(13).setCellValue(regis.getOlympiadName());
            row.createCell(14).setCellValue(regis.getTelegram());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = "registrations.xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        workbook.write(response.getOutputStream());
        response.getOutputStream().flush();
        workbook.close();
    }
}
