package kg.edu.mathbilim.repository.post;

import kg.edu.mathbilim.model.post.PostTypeTranslation;
import kg.edu.mathbilim.repository.abstracts.AbstractTypeTranslationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostTypeTranslationRepository extends AbstractTypeTranslationRepository<PostTypeTranslation> {
    default List<PostTypeTranslation> findByPostTypeId(Integer postTypeId) {
        return findByTypeId(postTypeId);
    }

    default Optional<PostTypeTranslation> findByPostTypeIdAndLanguageCode(Integer postTypeId,
                                                                          String languageCode) {
        return findByTypeIdAndLanguageCode(postTypeId, languageCode);
    }

    default void deleteByPostTypeId(Integer postTypeId) {
        deleteByTypeId(postTypeId);
    }

    default boolean existsByPostTypeIdAndLanguageCode(Integer postTypeId, String languageCode) {
        return existsByTypeIdAndLanguageCode(postTypeId, languageCode);
    }
}