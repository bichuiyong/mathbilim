package kg.edu.mathbilim.service.interfaces.reference;


import kg.edu.mathbilim.dto.reference.PostTypeDto;

import java.util.List;

public interface PostTypeService {
    PostTypeDto getPostTypeByName(String name);

    List<PostTypeDto> getAllPostTypes();

    boolean existByPostType(String postType);

    PostTypeDto createPostType(PostTypeDto postTypeDto);

    void deletePostType(Integer postType);

    PostTypeDto updatePostType(PostTypeDto postTypeDto);
}
