package kg.edu.mathbilim.mapper.news;

import kg.edu.mathbilim.dto.news.NewsDto;
import kg.edu.mathbilim.model.news.News;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    NewsDto toDto(News news);
    News toEntity(NewsDto newsDto);
}
