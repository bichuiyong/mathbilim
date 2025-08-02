package kg.edu.mathbilim.repository.olympiad;

import kg.edu.mathbilim.model.olympiad.OlympiadStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface OlympiadStageRepository extends JpaRepository<OlympiadStage, Integer> {
    List<OlympiadStage> getOlympiadStageByOlympiad_IdOrderByStageOrderAsc(Long olympiadId);

    void deleteByOlympiadId(Long olympiadId);

    Optional<OlympiadStage> findById(Long stageId);
}
