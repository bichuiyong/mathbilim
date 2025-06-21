package kg.edu.mathbilim.dto;

import jakarta.validation.constraints.NotBlank;
import kg.edu.mathbilim.dto.user.UserDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
    private Long id;

    private UserDto author;

    @NotBlank(message = "Комментарий не должен быть пустым")
    private String content;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    private Instant updatedAt = Instant.now();
}
