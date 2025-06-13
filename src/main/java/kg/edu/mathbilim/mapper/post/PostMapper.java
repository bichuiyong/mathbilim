package kg.edu.mathbilim.mapper.post;

import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.model.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "type.id", source = "typeId")
    Post toEntity(PostDto dto);

    @Mapping(target = "typeId", source = "type.id")
    PostDto toDto(Post post);
}
