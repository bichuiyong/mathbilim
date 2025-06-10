package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    @Query("""
                SELECT DISTINCT f FROM File f
                WHERE LOWER(f.filename) LIKE LOWER(CONCAT('%', :query, '%')) 
                   OR LOWER(f.filePath) LIKE LOWER(CONCAT('%', :query, '%')) 
            """)
    Page<File> findByQuery(@Param("query") String query, Pageable pageable);

    @Query("""
                SELECT DISTINCT f FROM File f
                WHERE f.user.email =:userEmail AND 
                   (LOWER(f.filename) LIKE LOWER(CONCAT('%', :query, '%')) 
                   OR LOWER(f.filePath) LIKE LOWER(CONCAT('%', :query, '%')) )
            """)
    Page<File> findByUserWithQuery(@Param("userEmail") String userEmail, @Param("query") String query, Pageable pageable);

    Page<File> findByUser_Email(String userId, Pageable pageable);

    Optional<File> findByFilenameIgnoreCase(String filename);

    List<File> findByFilePathContaining(String pathPattern);
}
