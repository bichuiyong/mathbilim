package kg.edu.mathbilim.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AfterGoogleAuthDto {
    @NotNull
    private Integer userTypeId;
    @NotBlank
    private String password;
}
