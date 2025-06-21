package kg.edu.mathbilim.dto.news;


import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.abstracts.ContentDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.Language;
import kg.edu.mathbilim.validation.annotation.AtLeastOneTranslationRequired;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsDto extends ContentDto {
    
    List<FileDto> newsFiles = new ArrayList<>();

    @AtLeastOneTranslationRequired
    @Builder.Default
    private List<NewsTranslationDto> newsTranslations = createDefaultTranslations();

    private static List<NewsTranslationDto> createDefaultTranslations() {
        return Arrays.stream(Language.values())
                .map(lang -> NewsTranslationDto.builder()
                        .languageCode(lang.getCode())
                        .title("")
                        .content("")
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getApprovedBy() {
        return null; 
    }

    @Override
    public void setApprovedBy(UserDto approvedBy) {
        // Игнорируем, так как поля нет в таблице
    }

    @Override
    public ContentStatus getStatus() {
        return null; // Поле отсутствует в News
    }

    @Override
    public void setStatus(ContentStatus status) {
        // Игнорируем, так как поля нет в таблице
    }
}
