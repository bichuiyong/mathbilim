package kg.edu.mathbilim.dto.blog;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.abstracts.ContentDto;
import kg.edu.mathbilim.util.TranslationUtil;
import kg.edu.mathbilim.validation.annotation.AllowedExtension;
import kg.edu.mathbilim.validation.annotation.AtLeastOneTranslationRequired;
import kg.edu.mathbilim.validation.annotation.NotEmptyMultipartFile;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogDto extends ContentDto {

    @AtLeastOneTranslationRequired
    @Builder.Default
    List<@Valid BlogTranslationDto> blogTranslations = createDefaultTranslations();

    @NotEmptyMultipartFile(message = "{blog.image.required}")
    @AllowedExtension({"jpg", "png", "jpeg"})
    MultipartFile mpMainImage;

    static List<BlogTranslationDto> createDefaultTranslations() {
        return TranslationUtil.createDefaultTranslations(languageCode ->
                BlogTranslationDto.builder()
                        .languageCode(languageCode)
                        .build()
        );
    }
}
