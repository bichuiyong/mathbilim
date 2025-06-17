package kg.edu.mathbilim.dto;

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

    private String content;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    private Instant updatedAt = Instant.now();
}
