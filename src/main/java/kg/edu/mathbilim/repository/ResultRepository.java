package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.Result;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByFile(File files);

    List<Result> findByOlympiad(Olympiad olympiad);

    List<Result> findByUser(User user);

    List<Result> findByUserIsNull();
}
