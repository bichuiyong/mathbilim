package kg.edu.mathbilim.mapper.post;

import kg.edu.mathbilim.dto.post.PostDto;
import kg.edu.mathbilim.mapper.post.PostTranslationMapper;
import kg.edu.mathbilim.model.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring", uses = {PostTranslationMapper.class})
public interface PostReadMapper {

//    Post toEntity(PostDto blogDto);

    @Mapping(target = "postTranslations", source = "postTranslations")
    @Mapping(target = "deleted", source = "deleted")
    PostDto toDto(Post post);
}
