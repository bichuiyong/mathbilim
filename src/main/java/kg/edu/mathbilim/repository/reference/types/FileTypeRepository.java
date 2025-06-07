package kg.edu.mathbilim.repository.reference.types;

import kg.edu.mathbilim.model.reference.types.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileTypeRepository extends JpaRepository<FileType, Integer> {
}
