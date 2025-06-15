package kg.edu.mathbilim.service.interfaces.post;


import kg.edu.mathbilim.dto.post.post_type.PostTypeDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostTypeService {

    List<PostTypeDto> getAllPostTypes();

    PostTypeDto getPostTypeById(Integer id);

    List<PostTypeDto> getPostTypesByLanguage(String languageCode);

    @Transactional
    PostTypeDto createPostType(PostTypeDto postTypeDto);

    @Transactional
    PostTypeDto updatePostType(Integer id, PostTypeDto postTypeDto);

    @Transactional
    void deletePostType(Integer id);

    @Transactional
    PostTypeDto addTranslation(Integer postTypeId, String languageCode, String translation);

    @Transactional
    PostTypeDto removeTranslation(Integer postTypeId, String languageCode);
}
