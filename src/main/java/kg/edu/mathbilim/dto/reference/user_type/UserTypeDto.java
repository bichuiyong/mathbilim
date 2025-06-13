package kg.edu.mathbilim.dto.reference.user_type;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTypeDto {
    private Integer id;

    private Set<UserTypeTranslationDto> userTypeTranslations;
}
