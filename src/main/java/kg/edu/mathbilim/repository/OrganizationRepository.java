package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    List<Organization> findByNameStartingWithAndDeletedFalse(String organizationName);

    Page<Organization> findByNameStartingWithAndDeletedFalse(String organizationName, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Organization o set o.deleted=true where o.id = :id")
    void deleteByIdAndSetDeleted(Long id);

    @Query("select o from Organization o where o.deleted=false and o.id=:id")
    Optional<Organization> findByIdAndByDeletedFalse(long id);

    List<Organization> findAllByDeletedFalse();

    Page<Organization> findAllByDeletedFalse(Pageable pageable);

}
