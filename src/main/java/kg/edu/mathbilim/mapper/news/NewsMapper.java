package kg.edu.mathbilim.mapper.news;

import kg.edu.mathbilim.dto.news.NewsDto;
import kg.edu.mathbilim.mapper.BaseMapper;
import kg.edu.mathbilim.model.news.News;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = NewsTranslationMapper.class)
public interface NewsMapper extends BaseMapper<News, NewsDto> {

    NewsDto toDto(News news);


    News toEntity(NewsDto newsDto);
}
