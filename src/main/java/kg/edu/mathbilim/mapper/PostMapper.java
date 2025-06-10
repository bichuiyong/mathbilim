package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.PostDto;
import kg.edu.mathbilim.model.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toEntity(PostDto dto);

    PostDto toDto(Post post);
}
