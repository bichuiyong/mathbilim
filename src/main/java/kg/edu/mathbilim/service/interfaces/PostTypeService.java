package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.EventTypeDto;
import kg.edu.mathbilim.model.EventType;
import kg.edu.mathbilim.model.PostType;

import java.util.List;

public interface PostTypeService {
    PostType getPostTypeByName(String name);
    List<PostType> getAllPostTypes();
}
