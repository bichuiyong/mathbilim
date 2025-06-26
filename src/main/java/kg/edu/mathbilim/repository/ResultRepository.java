package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Result;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByFile(File files);

    List<Result> findByOlympiad(Olympiad olympiad);
}
