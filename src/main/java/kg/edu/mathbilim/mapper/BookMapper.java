package kg.edu.mathbilim.mapper;

import kg.edu.mathbilim.dto.book.BookDto;
import kg.edu.mathbilim.dto.reference.CategoryTranslationDto;
import kg.edu.mathbilim.model.Book;
import kg.edu.mathbilim.model.reference.CategoryTranslation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper extends BaseMapper<Book, BookDto> {
    Book toEntity(BookDto dto);

    @Mapping(source = "creator", target = "creator")
    @Mapping(source = "mainImage", target = "mainImage")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "deleted", source = "deleted")
    BookDto toDto(Book book);

    default CategoryTranslationDto mapCategoryTranslation(CategoryTranslation translation) {
        if (translation == null) {
            return null;
        }

        CategoryTranslationDto dto = new CategoryTranslationDto();
        dto.setTypeId(translation.getId().getTypeId());
        dto.setLanguageCode(translation.getId().getLanguageCode());
        dto.setTranslation(translation.getTranslation());

        return dto;
    }

    default List<CategoryTranslationDto> mapCategoryTranslations(List<CategoryTranslation> translations) {
        if (translations == null) {
            return null;
        }

        return translations.stream()
                .map(this::mapCategoryTranslation)
                .collect(Collectors.toList());
    }
}
