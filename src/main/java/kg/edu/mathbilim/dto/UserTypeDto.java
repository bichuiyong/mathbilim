package kg.edu.mathbilim.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserTypeDto {
    private Integer id;

    @NotBlank
    private String name;
}
