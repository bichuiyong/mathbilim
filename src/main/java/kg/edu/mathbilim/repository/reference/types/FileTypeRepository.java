package kg.edu.mathbilim.repository.reference.types;

import kg.edu.mathbilim.model.reference.types.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileTypeRepository extends JpaRepository<FileType, Integer> {
    Optional<FileType> findByNameIgnoreCase(String name);

    Optional<FileType> findByMimeTypeIgnoreCase(String mimeType);

    Optional<FileType> findByExtensionIgnoreCase(String extension);

    List<FileType> findByMimeTypeStartingWith(String s);

    List<FileType> findByMimeTypeIn(List<String> documentMimes);

    boolean existsByMimeTypeIgnoreCase(String mimeType);
}
