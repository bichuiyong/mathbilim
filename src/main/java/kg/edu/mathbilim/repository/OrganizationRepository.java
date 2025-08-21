package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    List<Organization> findByNameStartingWith(String organizationName);

    Page<Organization> findByNameStartingWith(String organizationName, Pageable pageable);
}
