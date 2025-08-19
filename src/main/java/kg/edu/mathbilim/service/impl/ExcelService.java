package kg.edu.mathbilim.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import kg.edu.mathbilim.dto.olympiad.RegistrationDto;
import kg.edu.mathbilim.dto.test.TestResultDto;
import kg.edu.mathbilim.dto.test.TopicResultDto;
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

    public void generateDetailedTestResult(TestResultDto dto, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle numberStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        numberStyle.setDataFormat(format.getFormat("0.00"));

        Sheet generalSheet = workbook.createSheet("Общий результат");
        Row headerRowGeneral = generalSheet.createRow(0);
        String[] generalHeaders = {
                "Название теста", "Дата завершения", "Количество вопросов",
                "Правильных ответов", "Общий балл", "Максимальный балл", "Процент"
        };

        for (int i = 0; i < generalHeaders.length; i++) {
            Cell cell = headerRowGeneral.createCell(i);
            cell.setCellValue(generalHeaders[i]);
            cell.setCellStyle(headerStyle);
        }

        Row dataRow = generalSheet.createRow(1);
        dataRow.createCell(0).setCellValue(dto.getTestName() != null ? dto.getTestName() : "Не указано");
        dataRow.createCell(1).setCellValue(dto.getFinished() != null ? dto.getFinished() : "Не завершено");
        Cell questionCountCell = dataRow.createCell(2);
        questionCountCell.setCellValue(dto.getQuestionCount() != null ? dto.getQuestionCount() : 0);
        questionCountCell.setCellType(CellType.NUMERIC);
        Cell correctAnswersCell = dataRow.createCell(3);
        correctAnswersCell.setCellValue(dto.getCorrectAnswersCount() != null ? dto.getCorrectAnswersCount() : 0);
        correctAnswersCell.setCellType(CellType.NUMERIC);
        Cell totalScoreCell = dataRow.createCell(4);
        totalScoreCell.setCellValue(dto.getTotalScoreCount() != null ? dto.getTotalScoreCount() : 0.0);
        totalScoreCell.setCellStyle(numberStyle);
        Cell maxScoreCell = dataRow.createCell(5);
        maxScoreCell.setCellValue(dto.getMaxScoreCount() != null ? dto.getMaxScoreCount() : 0.0);
        maxScoreCell.setCellStyle(numberStyle);
        Cell percentageCell = dataRow.createCell(6);
        percentageCell.setCellValue(dto.getTotalPercentage() != null ? dto.getTotalPercentage() : 0.0);
        percentageCell.setCellStyle(numberStyle);

        for (int i = 0; i < generalHeaders.length; i++) {
            generalSheet.autoSizeColumn(i);
        }

        Sheet topicsSheet = workbook.createSheet("Результаты по темам");
        Row headerRowTopics = topicsSheet.createRow(0);
        String[] topicsHeaders = {
                "Название темы", "Общий балл", "Максимальный балл", "Процент",
                "Доля в общем балле (%)", "Доля в максимальном балле (%)"
        };

        for (int i = 0; i < topicsHeaders.length; i++) {
            Cell cell = headerRowTopics.createCell(i);
            cell.setCellValue(topicsHeaders[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        List<TopicResultDto> topicList = dto.getTopicResultDtoList();
        double totalTestScore = dto.getTotalScoreCount() != null ? dto.getTotalScoreCount() : 1.0; // Избегаем деления на 0
        double maxTestScore = dto.getMaxScoreCount() != null ? dto.getMaxScoreCount() : 1.0;
        if (topicList != null && !topicList.isEmpty()) {
            for (TopicResultDto topic : topicList) {
                Row row = topicsSheet.createRow(rowNum++);
                row.createCell(0).setCellValue(topic.getTopicName() != null ? topic.getTopicName() : "Не указано");
                Cell topicScoreCell = row.createCell(1);
                topicScoreCell.setCellValue(topic.getTotalScoreCount() != null ? topic.getTotalScoreCount() : 0.0);
                topicScoreCell.setCellStyle(numberStyle);
                Cell topicMaxScoreCell = row.createCell(2);
                topicMaxScoreCell.setCellValue(topic.getMaxScoreCount() != null ? topic.getMaxScoreCount() : 0.0);
                topicMaxScoreCell.setCellStyle(numberStyle);
                Cell topicPercentageCell = row.createCell(3);
                topicPercentageCell.setCellValue(topic.getPercentage() != null ? topic.getPercentage() : 0.0);
                topicPercentageCell.setCellStyle(numberStyle);
                Cell scoreContributionCell = row.createCell(4);
                double scoreContribution = topic.getTotalScoreCount() != null ? (topic.getTotalScoreCount() / totalTestScore * 100) : 0.0;
                scoreContributionCell.setCellValue(scoreContribution);
                scoreContributionCell.setCellStyle(numberStyle);
                Cell maxScoreContributionCell = row.createCell(5);
                double maxScoreContribution = topic.getMaxScoreCount() != null ? (topic.getMaxScoreCount() / maxTestScore * 100) : 0.0;
                maxScoreContributionCell.setCellValue(maxScoreContribution);
                maxScoreContributionCell.setCellStyle(numberStyle);
            }
        } else {
            Row row = topicsSheet.createRow(rowNum);
            row.createCell(0).setCellValue("Данные по темам отсутствуют");
        }

        for (int i = 0; i < topicsHeaders.length; i++) {
            topicsSheet.autoSizeColumn(i);
        }

        Sheet categoriesSheet = workbook.createSheet("Статистика по категориям");
        Row headerRowCategories = categoriesSheet.createRow(0);
        String[] categoriesHeaders = {
                "Название категории", "Предполагаемое кол-во вопросов", "Предполагаемых правильных ответов",
                "Процент правильных ответов", "Средний балл на вопрос"
        };

        for (int i = 0; i < categoriesHeaders.length; i++) {
            Cell cell = headerRowCategories.createCell(i);
            cell.setCellValue(categoriesHeaders[i]);
            cell.setCellStyle(headerStyle);
        }

        rowNum = 1;
        int totalQuestions = dto.getQuestionCount() != null ? dto.getQuestionCount() : 0;
        int totalCorrectAnswers = dto.getCorrectAnswersCount() != null ? dto.getCorrectAnswersCount() : 0;
        double totalMaxScore = maxTestScore;

        if (topicList != null && !topicList.isEmpty()) {
            for (TopicResultDto topic : topicList) {
                Row row = categoriesSheet.createRow(rowNum++);
                row.createCell(0).setCellValue(topic.getTopicName() != null ? topic.getTopicName() : "Не указано");

                double topicMaxScore = topic.getMaxScoreCount() != null ? topic.getMaxScoreCount() : 0.0;
                double questionShare = totalMaxScore > 0 ? topicMaxScore / totalMaxScore : 0.0;
                int topicQuestions = (int) Math.round(totalQuestions * questionShare);
                Cell topicQuestionCountCell = row.createCell(1);
                topicQuestionCountCell.setCellValue(topicQuestions);
                topicQuestionCountCell.setCellType(CellType.NUMERIC);

                double topicScore = topic.getTotalScoreCount() != null ? topic.getTotalScoreCount() : 0.0;
                double correctAnswerShare = totalTestScore > 0 ? topicScore / totalTestScore : 0.0;
                int topicCorrectAnswers = (int) Math.round(totalCorrectAnswers * correctAnswerShare);
                Cell topicCorrectAnswersCell = row.createCell(2);
                topicCorrectAnswersCell.setCellValue(topicCorrectAnswers);
                topicCorrectAnswersCell.setCellType(CellType.NUMERIC);

                Cell correctPercentageCell = row.createCell(3);
                double correctPercentage = topicQuestions > 0 ? (topicCorrectAnswers * 100.0 / topicQuestions) : 0.0;
                correctPercentageCell.setCellValue(correctPercentage);
                correctPercentageCell.setCellStyle(numberStyle);

                Cell avgScorePerQuestionCell = row.createCell(4);
                double avgScore = topicQuestions > 0 ? (topicScore / topicQuestions) : 0.0;
                avgScorePerQuestionCell.setCellValue(avgScore);
                avgScorePerQuestionCell.setCellStyle(numberStyle);
            }
        } else {
            Row row = categoriesSheet.createRow(rowNum);
            row.createCell(0).setCellValue("Данные по категориям отсутствуют");
        }

        for (int i = 0; i < categoriesHeaders.length; i++) {
            categoriesSheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = "detailed_test_result.xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        workbook.write(response.getOutputStream());
        response.getOutputStream().flush();
        workbook.close();
    }
}
