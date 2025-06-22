package kg.edu.mathbilim.mapper.post;

import kg.edu.mathbilim.dto.post.PostTypeDto;
import kg.edu.mathbilim.model.post.PostType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostTypeMapper {
    PostType toEntity(PostTypeDto dto);

    PostTypeDto toDto(PostType event);
}
