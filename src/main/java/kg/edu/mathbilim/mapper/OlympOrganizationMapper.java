package kg.edu.mathbilim.mapper;


import kg.edu.mathbilim.dto.organization.OlympOrganizationDto;
import kg.edu.mathbilim.model.organization.OlympiadOrganization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OlympOrganizationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "olympiad", ignore = true)      // вручную устанавливаем
    @Mapping(target = "organization", ignore = true)  // вручную устанавливаем
    OlympiadOrganization toEntity(OlympOrganizationDto dto);
}
