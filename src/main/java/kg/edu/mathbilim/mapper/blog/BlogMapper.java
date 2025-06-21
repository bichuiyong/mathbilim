package kg.edu.mathbilim.mapper.blog;

import kg.edu.mathbilim.dto.blog.BlogDto;
import kg.edu.mathbilim.model.blog.Blog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlogMapper {

    Blog toEntity(BlogDto blogDto);

    BlogDto toDto(Blog blog);
}
