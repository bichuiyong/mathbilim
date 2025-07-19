package kg.edu.mathbilim.mapper.news;

import kg.edu.mathbilim.dto.news.NewsDto;
import kg.edu.mathbilim.mapper.BaseMapper;
import kg.edu.mathbilim.model.news.News;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface NewsMapper extends BaseMapper<News, NewsDto> {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "creator", source = "creator")
    NewsDto toDto(News news);


    News toEntity(NewsDto newsDto);
}
