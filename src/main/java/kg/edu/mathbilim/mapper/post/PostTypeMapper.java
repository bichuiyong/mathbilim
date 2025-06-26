package kg.edu.mathbilim.mapper.post;

import kg.edu.mathbilim.dto.post.PostTypeDto;
import kg.edu.mathbilim.dto.post.PostTypeTranslationDto;
import kg.edu.mathbilim.mapper.TypeBaseMapper;
import kg.edu.mathbilim.model.post.PostType;
import kg.edu.mathbilim.model.post.PostTypeTranslation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostTypeMapper extends TypeBaseMapper<
        PostType,
        PostTypeDto,
        PostTypeTranslation,
        PostTypeTranslationDto,
        PostTypeTranslationMapper> {

    @Override
    PostTypeDto toDto(PostType entity);

    @Override
    PostType toEntity(PostTypeDto dto);

    @Override
    default PostTypeTranslationMapper getTranslationMapper() {
        return new PostTypeTranslationMapperImpl();
    }
}