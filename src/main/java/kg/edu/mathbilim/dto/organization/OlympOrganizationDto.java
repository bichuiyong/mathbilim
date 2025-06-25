package kg.edu.mathbilim.dto.organization;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OlympOrganizationDto {
    Long olympiadId;
    Long organizationId;
}