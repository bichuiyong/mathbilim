package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.AuthorDto;
import kg.edu.mathbilim.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toEntity(AuthorDto dto);

    AuthorDto toDto(Author author);
}
