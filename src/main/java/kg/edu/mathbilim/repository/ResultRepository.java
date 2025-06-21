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
    List<Result> findByFiles(File files);

    List<Result> findByOlympiad(Olympiad olympiad);

    List<Result> findByUser(User user);

    List<Result> findByUserIsNull();

    @Query("SELECT r FROM Result r JOIN FETCH r.files JOIN FETCH r.olympiad WHERE r.id = :id")
    Optional<Result> findByIdWithAssociations(@Param("id") Long id);

    @Query("SELECT r FROM Result r WHERE r.createdAt BETWEEN :startDate AND :endDate")
    List<Result> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);
}
