package kg.edu.mathbilim.repository.olympiad;

import kg.edu.mathbilim.dto.olympiad.OlympiadStageDto;
import kg.edu.mathbilim.model.olympiad.OlympiadStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OlympiadStageRepository extends JpaRepository<OlympiadStage, Integer> {
    List<OlympiadStage> getOlympiadStageByOlympiad_Id(Integer olympiadId);
}
