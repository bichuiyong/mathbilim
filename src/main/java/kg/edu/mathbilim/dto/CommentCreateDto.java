package kg.edu.mathbilim.dto;

import kg.edu.mathbilim.dto.user.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDto {
    private String content;
    private UserDto author;
}