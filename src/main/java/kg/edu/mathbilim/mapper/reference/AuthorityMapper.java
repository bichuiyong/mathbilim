package kg.edu.mathbilim.mapper.reference;

import kg.edu.mathbilim.dto.reference.AuthorityDto;
import kg.edu.mathbilim.model.reference.Authority;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    Authority toEntity(AuthorityDto dto);

    AuthorityDto toDto(Authority entity);
}
