package kg.edu.mathbilim.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentBlockDto {
    private String type;
    private String value;
}
