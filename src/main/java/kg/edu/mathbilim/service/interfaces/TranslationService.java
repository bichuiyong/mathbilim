package kg.edu.mathbilim.service.interfaces;

import kg.edu.mathbilim.dto.event.EventTypeDto;
import kg.edu.mathbilim.dto.post.PostTypeDto;
import kg.edu.mathbilim.dto.reference.CategoryDto;
import kg.edu.mathbilim.dto.user.UserTypeDto;

import java.util.List;
import java.util.Locale;

public interface TranslationService {
//    Locale getCurrentLocale();

    List<UserTypeDto> getUserTypesByLanguage();

    List<CategoryDto> getCategoriesByLanguage();

    List<EventTypeDto> getEventTypesByLanguage();

    List<PostTypeDto> getPostTypesByLanguage();
}
