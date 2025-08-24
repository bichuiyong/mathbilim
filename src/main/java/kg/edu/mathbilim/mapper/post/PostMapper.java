package kg.edu.mathbilim.mapper.post;

import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.mapper.BaseMapper;
import kg.edu.mathbilim.mapper.news.NewsTranslationMapper;
import kg.edu.mathbilim.model.post.Post;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper extends BaseMapper<Post, PostDto> {
    @Mapping(target = "type.id", source = "typeId")
    Post toEntity(PostDto dto);

    @Mapping(target = "postTranslations", source = "postTranslations")
    @Mapping(target = "typeId", source = "type.id")
    @Mapping(target = "deleted", source = "deleted")
    PostDto toDto(Post post);
}
