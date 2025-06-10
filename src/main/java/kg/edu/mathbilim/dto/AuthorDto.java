package kg.edu.mathbilim.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDto {
    private Long id;

    private String name;

    private String middleName;

    private String surname;
}
