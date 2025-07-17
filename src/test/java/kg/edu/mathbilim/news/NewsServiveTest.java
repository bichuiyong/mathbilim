package kg.edu.mathbilim.news;

import jakarta.validation.constraints.AssertTrue;
import kg.edu.mathbilim.mapper.news.NewsMapper;
import kg.edu.mathbilim.mapper.news.NewsTranslationMapper;
import kg.edu.mathbilim.model.news.News;
import kg.edu.mathbilim.model.news.NewsTranslation;
import kg.edu.mathbilim.model.user.User;
import kg.edu.mathbilim.repository.news.NewsRepository;
import kg.edu.mathbilim.repository.news.NewsTranslationRepository;
import kg.edu.mathbilim.service.impl.news.NewsServiceImpl;
import kg.edu.mathbilim.service.impl.news.NewsTranslationServiceImpl;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.news.NewsService;
import kg.edu.mathbilim.service.interfaces.news.NewsTranslationService;
import kg.edu.mathbilim.service.interfaces.notification.UserNotificationService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class NewsServiveTest {
    @Mock
    private NewsRepository newsRepository;

    private NewsServiceImpl newsService;
    @Mock
    private NewsTranslationService newsTranslationsService;
    @Mock
    private NewsMapper newsMapper;
    @Mock
    private UserService userService;
    @Mock
    private NewsTranslationRepository newsTranslationRepository;
    @Mock
    private FileService fileService;
    @Mock
    private UserNotificationService userNotificationService;

    private static News news;

    @BeforeAll
    public static void setUpBeforeClass(){
        news = News.builder()
                .id(1l)
                .newsFiles(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .creator(new User())
                .build();
    }



    @BeforeEach
    public  void init(){
        newsService = new NewsServiceImpl(newsRepository,newsMapper, userService,fileService,newsTranslationsService,newsTranslationRepository,userNotificationService);
    }

    @Test
    public void isUpdateWorkingCorrectly(){
        News n = new News();
        n.setId(1l);
        n.setShareCount(12l);
        n.setUpdatedAt(LocalDateTime.now());
        newsRepository.saveAndFlush(n);

        when(newsRepository.findById(1L)).thenReturn(Optional.of(n));

        News s = newsService.findByNewsId(n.getId());
        assertThat(s.getId()==1l);
        assertThat(s.getShareCount()==12l);

    }
}
