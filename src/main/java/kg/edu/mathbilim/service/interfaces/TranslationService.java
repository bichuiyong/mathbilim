package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.reference.category.CategoryDto;
import kg.edu.mathbilim.dto.reference.event_type.EventTypeDto;
import kg.edu.mathbilim.dto.reference.post_type.PostTypeDto;
import kg.edu.mathbilim.dto.reference.user_type.UserTypeDto;

import java.util.List;

public interface TranslationService {
    List<UserTypeDto> getUserTypesByLanguage();

    List<CategoryDto> getCategoriesByLanguage();

    List<EventTypeDto> getEventTypesByLanguage();

    List<PostTypeDto> getPostTypesByLanguage();
}
