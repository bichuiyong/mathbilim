package kg.edu.mathbilim.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String password;

    @Builder.Default
    private Boolean enabled = true;

    private Boolean isEmailVerified;

    @Builder.Default
    private String preferredLanguage = "ru";

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    private RoleDto role;

    private UserTypeDto type;
}
