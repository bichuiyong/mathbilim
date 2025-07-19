package kg.edu.mathbilim.service.impl.reference;

import kg.edu.mathbilim.dto.reference.CategoryTranslationDto;
import kg.edu.mathbilim.mapper.reference.CategoryTranslationMapper;
import kg.edu.mathbilim.model.reference.CategoryTranslation;
import kg.edu.mathbilim.repository.reference.CategoryTranslationRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTypeTranslationService;
import kg.edu.mathbilim.service.interfaces.reference.CategoryTranslationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryTranslationServiceImpl extends AbstractTypeTranslationService<
        CategoryTranslation,
        CategoryTranslationDto,
        CategoryTranslationRepository,
        CategoryTranslationMapper> implements CategoryTranslationService {

    public CategoryTranslationServiceImpl(CategoryTranslationRepository repository,
                                          CategoryTranslationMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected String getNotFoundMessage() {
        return "Перевод для этой категории не был найден";
    }

    @Override
    public List<CategoryTranslationDto> getTranslationsByCategoryId(Integer categoryId) {
        return super.getTranslationsByTypeId(categoryId);
    }

    @Override
    public void deleteAllTranslationsByCategoryId(Integer categoryId) {
        super.deleteAllTranslationsByTypeId(categoryId);
    }
}