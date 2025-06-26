package kg.edu.mathbilim.repository.reference;

import kg.edu.mathbilim.model.reference.CategoryTranslation;
import kg.edu.mathbilim.repository.abstracts.AbstractTypeTranslationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryTranslationRepository
        extends AbstractTypeTranslationRepository<CategoryTranslation> {
//
//    default List<CategoryTranslation> findByCategoryId(Integer categoryId) {
//        return findByTypeId(categoryId);
//    }
//
//    default Optional<CategoryTranslation> findByCategoryIdAndLanguageCode(
//            Integer categoryId, String languageCode) {
//        return findByTypeIdAndLanguageCode(categoryId, languageCode);
//    }
//
//    default void deleteByCategoryId(Integer categoryId) {
//        deleteByTypeId(categoryId);
//    }
//
//    default boolean existsByCategoryIdAndLanguageCode(
//            Integer categoryId, String languageCode) {
//        return existsByTypeIdAndLanguageCode(categoryId, languageCode);
//    }
}