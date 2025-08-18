package kg.edu.mathbilim.dto.olympiad;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class OlympListDto {
    private Integer id;
    private LocalDateTime createdAt;
    private String title;
    private Long fileId;
}
