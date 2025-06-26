package kg.edu.mathbilim.dto.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationIdNameDto {
    private Long id;
    private String name;
}
