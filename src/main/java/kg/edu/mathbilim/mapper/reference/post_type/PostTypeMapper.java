package kg.edu.mathbilim.mapper.reference.post_type;

import kg.edu.mathbilim.dto.reference.post_type.PostTypeDto;
import kg.edu.mathbilim.model.reference.post_type.PostType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostTypeMapper {
    PostType toEntity(PostTypeDto dto);

    PostTypeDto toDto(PostType event);
}
