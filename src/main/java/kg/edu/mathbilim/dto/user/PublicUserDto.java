package kg.edu.mathbilim.dto.user;

import kg.edu.mathbilim.model.user.UserType;
import kg.edu.mathbilim.model.user.UserTypeTranslation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class PublicUserDto  {
    private Long id;
    private String name;
    private String surname;
    private UserType type;
}
