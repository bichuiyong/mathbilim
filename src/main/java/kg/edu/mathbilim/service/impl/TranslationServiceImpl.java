package kg.edu.mathbilim.service.impl;

import kg.edu.mathbilim.dto.event.EventTypeDto;
import kg.edu.mathbilim.dto.post.PostTypeDto;
import kg.edu.mathbilim.dto.reference.CategoryDto;
import kg.edu.mathbilim.dto.user.UserTypeDto;
import kg.edu.mathbilim.service.interfaces.TranslationService;
import kg.edu.mathbilim.service.interfaces.event.EventTypeService;
import kg.edu.mathbilim.service.interfaces.post.PostTypeService;
import kg.edu.mathbilim.service.interfaces.reference.CategoryService;
import kg.edu.mathbilim.service.interfaces.reference.UserTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {
    private final UserTypeService userTypeService;
    private final CategoryService categoryService;
    private final EventTypeService eventTypeService;
    private final PostTypeService postTypeService;

    public Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }

    @Override
    public List<UserTypeDto> getUserTypesByLanguage() {
        return userTypeService.getUserTypesByLanguage(getCurrentLocale().getLanguage());
    }

    @Override
    public List<CategoryDto> getCategoriesByLanguage() {
        return categoryService.getCategoriesByLanguage(getCurrentLocale().getLanguage());
    }
    @Override
    public List<EventTypeDto> getEventTypesByLanguage() {
        return eventTypeService.getEventTypesByLanguage(getCurrentLocale().getLanguage());
    }
    @Override
    public List<PostTypeDto> getPostTypesByLanguage() {
        return postTypeService.getPostTypesByLanguage(getCurrentLocale().getLanguage());
    }


}
