package kg.edu.mathbilim.dto.organization;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OlympOrganizationDto {
    Long olympiadId;
    Long organizationId;
}