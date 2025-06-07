package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.PostTypeDto;
import kg.edu.mathbilim.model.PostType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostTypeMapper {
    PostType toEntity(PostTypeDto dto);

    PostTypeDto toDto(PostType postType);
}
