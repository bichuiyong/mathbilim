package kg.edu.mathbilim.mapper.reference.role;

import kg.edu.mathbilim.dto.reference.role.AuthorityDto;
import kg.edu.mathbilim.model.reference.role.Authority;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    Authority toEntity(AuthorityDto dto);

    AuthorityDto toDto(Authority entity);
}
