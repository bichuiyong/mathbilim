package kg.edu.mathbilim.dto.test;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.validation.annotation.ValidTimeLimit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidTimeLimit(min = 1, max = 500)
public class TestCreateDto {
    @NotEmpty
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    private MultipartFile file;

    @NotNull
    private Boolean hasLimit;

    private Integer timeLimit;

    @Valid
    @NotEmpty(message = "Добавьте к тесту вопрос(ы)")
    private List<QuestionDto> questions;
}
