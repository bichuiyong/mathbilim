package kg.edu.mathbilim.dto.olympiad;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class OlympListDto {
    private Integer id;
    private String title;
    private Long fileId;
}
