package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByFilenameIgnoreCase(String filename);

    List<File> findByFilePathContaining(String pathPattern);
}
