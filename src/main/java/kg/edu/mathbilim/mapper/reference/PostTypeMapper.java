package kg.edu.mathbilim.mapper.reference;

import kg.edu.mathbilim.dto.reference.PostTypeDto;
import kg.edu.mathbilim.model.reference.PostType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostTypeMapper {
    PostType toEntity(PostTypeDto dto);

    PostTypeDto toDto(PostType event);
}
