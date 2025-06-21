package kg.edu.mathbilim.mapper;


import kg.edu.mathbilim.dto.organization.OlympOrganizationDto;
import kg.edu.mathbilim.model.organization.OlympiadOrganization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OlympOrganizationMapper {

    @Mapping(target = "olympiadId", source = "olympiad.id")
    @Mapping(target = "organizationId", source = "organization.id")
    OlympOrganizationDto toDto(OlympiadOrganization olympiadOrganization);

    @Mapping(target = "olympiad", ignore = true)
    @Mapping(target = "organization", ignore = true)
    OlympiadOrganization toEntity(OlympOrganizationDto dto);
}
