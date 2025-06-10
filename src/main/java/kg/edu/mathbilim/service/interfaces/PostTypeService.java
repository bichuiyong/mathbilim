package kg.edu.mathbilim.service.interfaces;


import kg.edu.mathbilim.dto.PostTypeDto;

import java.util.List;

public interface PostTypeService {
    PostTypeDto getPostTypeByName(String name);

    List<PostTypeDto> getAllPostTypes();

    boolean existByPostType(String postType);

    PostTypeDto createPostType(PostTypeDto postTypeDto);

    void deletePostType(Integer postType);

    PostTypeDto updatePostType(PostTypeDto postTypeDto);
}
