package kg.edu.mathbilim.dto.blog;

import kg.edu.mathbilim.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BlogCommentDto extends CommentDto {
    private Long blogId;
}
