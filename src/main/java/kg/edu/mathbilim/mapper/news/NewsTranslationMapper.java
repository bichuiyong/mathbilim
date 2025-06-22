package kg.edu.mathbilim.mapper.news;

import kg.edu.mathbilim.dto.news.NewsTranslationDto;
import kg.edu.mathbilim.mapper.BaseMapper;
import kg.edu.mathbilim.model.news.NewsTranslation;
import kg.edu.mathbilim.model.news.NewsTranslationId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NewsTranslationMapper extends BaseMapper<NewsTranslation, NewsTranslationDto> {
    @Mapping(source = "id.languageCode" , target = "languageCode")
    @Mapping(source="id.newsId", target = "newsId")
    NewsTranslationDto toDto(NewsTranslation translation);

    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target="id.newsId",source="newsId")
    @Mapping(target="news.id", source="newsId")
    @Mapping(target="news.newsTranslations", ignore = true)
    NewsTranslation toEntity(NewsTranslationDto translation);

    @AfterMapping
    default void ensureCompositeKey(@MappingTarget NewsTranslation entity, NewsTranslationDto dto) {
        if (entity.getId() == null) {
            entity.setId(new NewsTranslationId());
            entity.getId().setNewsId(dto.getNewsId());
            entity.getId().setLanguageCode(dto.getLanguageCode());
        }
    }

}
