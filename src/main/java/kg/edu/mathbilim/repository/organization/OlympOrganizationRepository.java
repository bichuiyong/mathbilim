package kg.edu.mathbilim.repository.organization;

import kg.edu.mathbilim.model.organization.OlympiadOrganization;
import kg.edu.mathbilim.model.organization.OlympiadOrganizationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OlympOrganizationRepository extends JpaRepository<OlympiadOrganization, OlympiadOrganizationKey> {
    @Query("SELECT oo FROM OlympiadOrganization oo WHERE oo.olympiad.id = :olympiadId")
    List<OlympiadOrganization> findByOlympiadId(@Param("olympiadId") Long olympiadId);

    @Query("SELECT oo FROM OlympiadOrganization oo WHERE oo.organization.id = :organizationId")
    List<OlympiadOrganization> findByOrganizationId(@Param("organizationId") Long organizationId);

    @Query("select o.organization.id from OlympiadOrganization o where o.olympiad.id = :olympiad_id ")
    List<Long> getOrganizationIdsWhereOlympiadId(Integer olympiad_id);

    void deleteByOlympiadId(Long olympiadId);
}
