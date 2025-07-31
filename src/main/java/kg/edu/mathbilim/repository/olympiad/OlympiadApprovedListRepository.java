package kg.edu.mathbilim.repository.olympiad;

import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.model.olympiad.OlympiadApprovedList;
import kg.edu.mathbilim.model.olympiad.OlympiadStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OlympiadApprovedListRepository extends JpaRepository<OlympiadApprovedList, Long> {
    Optional<OlympiadApprovedList> findByOlympiadStage(@NotNull OlympiadStage olympiadStage);
}
