package kg.edu.mathbilim.mapper.blog;

import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.mapper.BaseMapper;
import kg.edu.mathbilim.mapper.news.NewsTranslationMapper;
import kg.edu.mathbilim.model.blog.Blog;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        uses = { BlogTranslationMapper.class })
public interface BlogMapper extends BaseMapper<Blog, BlogDto> {

    @Mapping(target = "blogTranslations", source = "blogTranslations")
    Blog toEntity(BlogDto blogDto);

    @Mapping(target = "blogTranslations", source = "blogTranslations")
    BlogDto toDto(Blog blog);
}
