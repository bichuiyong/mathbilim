package kg.edu.mathbilim.repository;

import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Result;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.olympiad.OlympiadStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByFile(File files);

    Optional<Result> findByOlympiadStage(@NotNull OlympiadStage olympiadStage);
}
