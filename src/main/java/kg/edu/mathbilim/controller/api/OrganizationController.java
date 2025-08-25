package kg.edu.mathbilim.controller.api;

import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.OrganizationDto;
import kg.edu.mathbilim.dto.organization.OrganizationIdNameDto;
import kg.edu.mathbilim.service.interfaces.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<OrganizationDto> getOrganizationById(@PathVariable Long id) {
        try {
            OrganizationDto organization = organizationService.getById(id);
            return ResponseEntity.ok(organization);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("page")
    public ResponseEntity<Page<OrganizationDto>> getOrganizationsPage(@RequestParam(required = false) String name,
                                                                      Pageable pageable) {
        return ResponseEntity.ok(organizationService.getOrganizations(name, pageable));
    }

    @PutMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OrganizationDto> updateOrganization(@PathVariable Long id,
                                                              @Valid @ModelAttribute OrganizationDto organizationDto,
                                                              @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile) {
        return ResponseEntity.ok(organizationService.update(id, organizationDto, avatarFile));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        return organizationService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
