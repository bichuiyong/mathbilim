package kg.edu.mathbilim.mapper.blog;

import kg.edu.mathbilim.dto.blog.BlogTranslationDto;
import kg.edu.mathbilim.mapper.BaseMapper;
import kg.edu.mathbilim.model.blog.BlogTranslation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlogTranslationMapper extends BaseMapper<BlogTranslation, BlogTranslationDto> {
    @Mapping(target = "id.languageCode", source = "languageCode")
    @Mapping(target = "id.blogId", source = "blogId")
    @Mapping(target = "blog.id", source = "blogId")
    @Mapping(target = "blog.blogTranslations", ignore = true)
    BlogTranslation toEntity(BlogTranslationDto blogTranslation);

    @Mapping(source = "id.languageCode", target = "languageCode")
    @Mapping(source = "id.blogId", target = "blogId")
    BlogTranslationDto toDto(BlogTranslation blogTranslation);
}
