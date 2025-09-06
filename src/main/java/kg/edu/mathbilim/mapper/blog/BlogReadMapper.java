package kg.edu.mathbilim.mapper.blog;

import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.model.blog.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring", uses = {BlogTranslationMapper.class})
public interface BlogReadMapper {

//    Blog toEntity(BlogDto blogDto);

    @Mapping(target = "blogTranslations", source = "blogTranslations")
    @Mapping(target = "deleted", source = "deleted")
    BlogDto toDto(Blog blog);
}
