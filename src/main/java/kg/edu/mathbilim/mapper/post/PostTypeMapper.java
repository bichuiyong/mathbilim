package kg.edu.mathbilim.mapper.post;

import kg.edu.mathbilim.dto.post.post_type.PostTypeDto;
import kg.edu.mathbilim.model.post.post_type.PostType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostTypeMapper {
    PostType toEntity(PostTypeDto dto);

    PostTypeDto toDto(PostType event);
}
