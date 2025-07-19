package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.dto.organization.OrganizationIdNameDto;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("restOrganizations")
@RequiredArgsConstructor
@RequestMapping("api/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<List<OrganizationDto>> getAllOrganizations(@RequestParam(required = false) String name) {
        return ResponseEntity.ofNullable(organizationService.getOrganizations(name));
    }

    @GetMapping("name")
    public ResponseEntity<List<OrganizationIdNameDto>> getOrganizationsOnlyWithNameAndId() {
        return ResponseEntity.status(HttpStatus.OK).body(organizationService.getAllOrganizationIdNames());
    }

    @GetMapping("{id}")
    public ResponseEntity<OrganizationDto> getOrganization(@PathVariable Long id) {
        return ResponseEntity.ofNullable(organizationService.getById(id));
    }
}
