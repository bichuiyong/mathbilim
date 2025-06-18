package kg.edu.mathbilim.service.impl.blog;

import kg.edu.mathbilim.repository.blog.BlogTranslationRepository;
import kg.edu.mathbilim.service.interfaces.blog.BlogTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogTranslationServiceImpl implements BlogTranslationService {
    private final BlogTranslationRepository blogTranslationRepository;
}
