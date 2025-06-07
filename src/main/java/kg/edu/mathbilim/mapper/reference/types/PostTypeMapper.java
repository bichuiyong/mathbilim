package kg.edu.mathbilim.mapper.reference.types;

import kg.edu.mathbilim.dto.reference.types.PostTypeDto;
import kg.edu.mathbilim.model.reference.types.PostType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostTypeMapper {
    PostType toEntity(PostTypeDto dto);

    PostTypeDto toDto(PostType postType);
}
