package kg.edu.mathbilim.repository.abstracts;

import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.model.abstracts.AdminContent;
import kg.edu.mathbilim.model.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface BaseContentRepository<T extends AdminContent> extends JpaRepository<T, Long> {
    default Page<T> findByQuery(String query, Pageable pageable) {
        return findAll(pageable);
    }

    void incrementViewCount(Long id);

    void incrementShareCount(Long id);

    Page<T> findByCreator_Id(Long creatorId, Pageable pageable);
}