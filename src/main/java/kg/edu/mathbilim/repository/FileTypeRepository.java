package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileTypeRepository extends JpaRepository<FileType, Integer> {
}
