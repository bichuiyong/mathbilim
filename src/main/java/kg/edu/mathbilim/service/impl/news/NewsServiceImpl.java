package kg.edu.mathbilim.service.impl.news;

import kg.edu.mathbilim.dto.news.CreateNewsDto;
import kg.edu.mathbilim.dto.news.NewsDto;
import kg.edu.mathbilim.dto.news.NewsTranslationDto;
import kg.edu.mathbilim.exception.nsee.NewsNotFoundException;
import kg.edu.mathbilim.mapper.news.NewsMapper;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.news.News;
import kg.edu.mathbilim.model.notifications.NotificationEnum;
import kg.edu.mathbilim.model.news.NewsTranslation;
import kg.edu.mathbilim.model.news.NewsTranslationId;
import kg.edu.mathbilim.repository.news.NewsRepository;
import kg.edu.mathbilim.repository.news.NewsTranslationRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTranslatableContentService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.news.NewsService;
import kg.edu.mathbilim.service.interfaces.news.NewsTranslationService;
import kg.edu.mathbilim.telegram.service.NotificationData;
import kg.edu.mathbilim.telegram.service.NotificationFacade;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NewsServiceImpl extends
        AbstractTranslatableContentService<
                News,
                NewsDto,
                NewsTranslationDto,
                NewsRepository,
                NewsMapper,
                NewsTranslationService
                >
        implements NewsService {

    public NewsServiceImpl(NewsRepository repository, NewsMapper mapper, UserService userService, FileService fileService, NewsTranslationService translationService, NewsTranslationRepository newsTranslationRepository, NotificationFacade notificationService, MessageSource messageSource) {
        super(repository, mapper, userService, fileService, translationService, notificationService, messageSource);
        this.newsTranslationRepository = newsTranslationRepository;
    }
    private final NewsTranslationRepository newsTranslationRepository;;

    @Override
    protected RuntimeException getNotFoundException() {
        return new NoSuchElementException("News not found");
    }

    @Override
    protected String getEntityName() {
        return "news";
    }

    @Override
    protected String getFileUploadPath(News entity) {
        return "news/" + entity.getId();
    }

    @Override
    protected List<File> uploadEntityFiles(MultipartFile[] attachments, News entity) {
        return fileService.uploadFilesForNews(attachments, entity);
    }

    @Override
    protected void setEntityFiles(News entity, List<File> files) {
        entity.setNewsFiles(files);
    }

    @Override
    protected List<NewsTranslationDto> getTranslationsFromDto(NewsDto dto) {
        return dto.getNewsTranslations();
    }

    @Override
    protected void setupDtoBeforeSave(NewsDto dto) {
        dto.setCreator(userService.getAuthUser());
    }

    @Transactional
    @Override
    public NewsDto create(CreateNewsDto createNewsDto) {
        if (createNewsDto.getImage() == null || createNewsDto.getImage().isEmpty()) {
            String errorMessage = messageSource.getMessage(
                    "content.main-image.required",
                    null,
                    LocaleContextHolder.getLocale()
            );
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }

        News news = News.builder()
                .creator(userService.getAuthUserEntity())
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(news);

        String titleForNotification = "Без заголовка";
        String contentForNotification = "Без описания";

        for (NewsTranslationDto translationDto : createNewsDto.getNews().getNewsTranslations()) {
            if ((translationDto.getTitle() != null && !translationDto.getTitle().isBlank()) ||
                    (translationDto.getContent() != null && !translationDto.getContent().isBlank())) {

                String title = translationDto.getTitle();
                String content = translationDto.getContent();

                NewsTranslationId translationId = new NewsTranslationId();
                translationId.setNewsId(news.getId());
                translationId.setLanguageCode(translationDto.getLanguageCode());

                NewsTranslation translation = NewsTranslation.builder()
                        .id(translationId)
                        .news(news)
                        .title(title)
                        .content(content)
                        .build();

                newsTranslationRepository.save(translation);

                if (!titleForNotification.equals("Без заголовка")) continue;
                titleForNotification = title != null ? title : titleForNotification;
                contentForNotification = content != null ? content : contentForNotification;
            }
        }

        uploadMainImage(createNewsDto.getImage(), news);
        uploadFiles(createNewsDto.getAttachments(), news);

        repository.saveAndFlush(news);

        NewsDto newsDto = mapper.toDto(news);

        sendNewsNotification(titleForNotification, contentForNotification, newsDto);

        return newsDto;
    }

    private void sendNewsNotification(String title, String content, NewsDto newsDto) {
        NotificationData nt = NotificationData.builder()
                .id(newsDto.getId())
                .message("Новая новость")
                .title(title)
                .mainImageId(newsDto.getMainImageId())
                .description(content)
                .contentId(newsDto.getId())
                .build();

        notificationFacade.notifyAllSubscribed(NotificationEnum.NEWS, nt);
    }


    @Override
    public Page<NewsDto> getContentByCreatorIdNews(Long creatorId, Pageable pageable, String query) {
        if (query != null) {
            Page<News> allBlogWithQuery = repository.getNewsByStatusWithQuery(query, creatorId, pageable);

            return allBlogWithQuery.map(mapper::toDto);
        }

        Page<News> allBlogs = repository.getNewsByCreator(creatorId, pageable);

        return allBlogs.map(mapper::toDto);
    }



    @Override
    public Page<NewsDto> getHistoryNews(Long creatorId, Pageable pageable, String query, String status) {
        if (query != null && !query.trim().isEmpty()) {
            return repository.getNewsWithQuery(creatorId, query, pageable)
                    .map(mapper::toDto);
        }

        return getContentByCreatorId(creatorId, pageable);
    }

    @Override
    public Page<NewsDto> getAllNews(Pageable pageable, String query) {
        if (query != null && !query.trim().isEmpty()) {
            return repository.getNewsWithQuery(query, pageable)
                    .map(mapper::toDto);
        }

        return repository.findAllByDeletedFalse(pageable)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public NewsDto getNewsById(Long id) {
        News news = repository.findById(id).orElseThrow(NewsNotFoundException::new);

        if (news.isDeleted()) {
            throw new NoSuchElementException("News with id " + id + " not found");
        }

        incrementViewCount(id);

        log.info("News {} with id {}", news.getId(), news.getCreator().getId());

        return mapper.toDto(news);
    }

    @Override
    public Page<NewsDto> getNewsByLang(String query, int page, int size, String sortBy, String sortDirection, String lang) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);


        Page<News> resultPage;

        if (query != null && !query.isEmpty()) {
            resultPage = repository.findNewsByQuery(query, lang, pageable);
        } else if (lang != null && !lang.isEmpty()) {
            resultPage = repository.findByNewsWithLang(lang, pageable);
        } else {
            resultPage = repository.findAllNews(pageable);
        }


        return PaginationUtil.getPage(() -> resultPage, mapper::toDto);
    }



    @Override
    public News findByNewsId(Long newsId) {
        return repository.findById(newsId).orElseThrow();
    }


    @Override
    public List<NewsDto> getNewsByMainPage() {
        List<News> news = repository.findTop10ByOrderByCreatedAtDesc();
        return news.stream()
                .filter(n -> !n.isDeleted())
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
