package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.CommentDto;
import kg.edu.mathbilim.model.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper extends BaseMapper<Comment, CommentDto> {
    Comment toEntity(CommentDto dto);

    CommentDto toDto(Comment comment);
}
