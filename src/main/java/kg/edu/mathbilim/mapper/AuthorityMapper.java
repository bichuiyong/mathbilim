package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.AuthorityDto;
import kg.edu.mathbilim.model.Authority;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    Authority toEntity(AuthorityDto dto);

    AuthorityDto toDto(Authority entity);
}
