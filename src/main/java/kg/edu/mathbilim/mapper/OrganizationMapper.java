package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.model.Organization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    Organization toEntity(OrganizationDto dto);

    OrganizationDto toDto(Organization entity);
}
