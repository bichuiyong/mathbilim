package kg.edu.mathbilim.repository.olympiad;

import kg.edu.mathbilim.dto.olympiad.RegistrationDto;
import kg.edu.mathbilim.model.olympiad.OlympiadStage;
import kg.edu.mathbilim.model.olympiad.Registration;
import kg.edu.mathbilim.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsByOlympiadStageAndUser(OlympiadStage olympiadStage, User user);

    @Query("""
    SELECT r FROM Registration r
    WHERE r.olympiadStage.id = :stageId
      AND (
          LOWER(r.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
          LOWER(r.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
          LOWER(r.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
          LOWER(r.school) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
          LOWER(r.classNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
          LOWER(r.region) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
          LOWER(r.district) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
          LOWER(r.locality) LIKE LOWER(CONCAT('%', :keyword, '%'))
      )
""")    Page<Registration> getByOlympiadStage_Id(@Param("stageId") Long stageId, @Param("keyword") String keyword, Pageable pageable);

    @Query("""
    SELECT r FROM Registration r
    WHERE r.olympiadStage.id = :stageId
""")
    List<Registration> getByOlympiadStage_IdForExcel(@Param("stageId") Long stageId);

}
