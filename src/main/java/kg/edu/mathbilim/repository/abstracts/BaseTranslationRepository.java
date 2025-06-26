package kg.edu.mathbilim.repository.abstracts;

import kg.edu.mathbilim.model.abstracts.ContentTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseTranslationRepository<T extends ContentTranslation, ID> extends JpaRepository<T, ID> {
}