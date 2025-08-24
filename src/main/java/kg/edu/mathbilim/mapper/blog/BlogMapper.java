package kg.edu.mathbilim.mapper.blog;

import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.mapper.BaseMapper;
import kg.edu.mathbilim.model.blog.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlogMapper extends BaseMapper<Blog, BlogDto> {

    Blog toEntity(BlogDto blogDto);

    @Mapping(target = "deleted", source = "deleted")
    BlogDto toDto(Blog blog);
}
