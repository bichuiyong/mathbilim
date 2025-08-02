package kg.edu.mathbilim.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordDto {
    @NotBlank
    @Size(min = 6, max = 24, message = "Длина должна быть >= 6 и <= 24")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).+$",
            message = "Should contain at least one uppercase letter and one number"
    )
    private String oldPassword;
    @NotBlank
    @Size(min = 6, max = 24, message = "Длина должна быть >= 6 и <= 24")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).+$",
            message = "Should contain at least one uppercase letter and one number"
    )
    private String newPassword;
}
