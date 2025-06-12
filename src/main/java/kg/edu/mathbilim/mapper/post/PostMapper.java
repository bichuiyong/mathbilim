package kg.edu.mathbilim.mapper.post;

import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.model.post.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toEntity(PostDto dto);

    PostDto toDto(Post post);
}
