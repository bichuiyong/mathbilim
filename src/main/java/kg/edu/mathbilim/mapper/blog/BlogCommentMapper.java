package kg.edu.mathbilim.mapper.blog;

import kg.edu.mathbilim.dto.blog.BlogCommentDto;
import kg.edu.mathbilim.model.blog.BlogComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlogCommentMapper {
    @Mapping(target = "comment.id", source = "id")
    @Mapping(target = "comment.content", source = "content")
    @Mapping(target = "comment.createdAt", source = "createdAt")
    @Mapping(target = "comment.updatedAt", source = "updatedAt")
    @Mapping(target = "comment.author", source = "author")
    @Mapping(target = "blog.id", source = "blogId")
    BlogComment toEntity(BlogCommentDto blogComment);

    @Mapping(source = "comment.id", target = "id")
    @Mapping(source = "comment.content", target = "content")
    @Mapping(source = "comment.createdAt", target = "createdAt")
    @Mapping(source = "comment.updatedAt", target = "updatedAt")
    @Mapping(source = "comment.author", target = "author")
    @Mapping(source = "blog.id", target = "blogId")
    BlogCommentDto toDto(BlogComment blogComment);
}
