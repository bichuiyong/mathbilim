package kg.edu.mathbilim.dto.olympiad;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationDto {
    @NotNull
    private Long user_id;
    @NotNull
    private Long olympiadStage_id;
    @Email
    private String email;
    @NotEmpty
    @Size(max = 100)
    private String region;
    @NotEmpty
    @Size(max = 100)
    private String district;
    @NotEmpty
    @Size(max = 200)
    private String fullName;
    @Size(max = 30)
    private String phoneNumber;
    @Size(max = 100)
    private String school;
    @Size(max = 100)
    private String telegram;
    @Size(max = 100)
    private String classNumber;
    @Size(max = 100)
    private String locality;
    @Size(max = 100)
    private String classTeacherFullName;
    @Size(max = 100)
    private String parentFullName;
    @Size(max = 100)
    private String parentPhoneNumber;
    @Size(max = 100)
    private String parentEmail;

}
