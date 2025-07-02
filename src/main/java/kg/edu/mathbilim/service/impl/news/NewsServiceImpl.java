package kg.edu.mathbilim.service.impl.news;

import kg.edu.mathbilim.dto.news.CreateNewsDto;
import kg.edu.mathbilim.dto.news.NewsDto;
import kg.edu.mathbilim.dto.news.NewsTranslationDto;
import kg.edu.mathbilim.mapper.news.NewsMapper;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.news.News;
import kg.edu.mathbilim.model.news.NewsTranslation;
import kg.edu.mathbilim.model.news.NewsTranslationId;
import kg.edu.mathbilim.repository.news.NewsRepository;
import kg.edu.mathbilim.repository.news.NewsTranslationRepository;
import kg.edu.mathbilim.service.impl.abstracts.AbstractTranslatableContentService;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.news.NewsService;
import kg.edu.mathbilim.service.interfaces.news.NewsTranslationService;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

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

    public NewsServiceImpl(NewsRepository repository, NewsMapper mapper, UserService userService, FileService fileService, NewsTranslationService translationService, NewsTranslationRepository newsTranslationRepository) {
        super(repository, mapper, userService, fileService, translationService);
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
        News news = News.builder()
                .creator(userService.getAuthUserEntity())
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(news);

        for (NewsTranslationDto translationDto : createNewsDto.getNews().getNewsTranslations()) {
            if ((translationDto.getTitle() != null && !translationDto.getTitle().isBlank()) ||
                    (translationDto.getContent() != null && !translationDto.getContent().isBlank())) {

                NewsTranslationId translationId = new NewsTranslationId();
                translationId.setNewsId(news.getId());
                translationId.setLanguageCode(translationDto.getLanguageCode());

                NewsTranslation translation = NewsTranslation.builder()
                        .id(translationId)
                        .news(news)
                        .title(translationDto.getTitle())
                        .content(translationDto.getContent())
                        .build();

                newsTranslationRepository.save(translation);
            }
        }

        uploadMainImage(createNewsDto.getImage(), news);

        uploadFiles(createNewsDto.getAttachments(), news);

        repository.saveAndFlush(news);

        log.info("Created News {} with id {}", news.getId());
        return mapper.toDto(news);
    }

    @Override
    public Page<NewsDto> getNewsByLang(String query, int page, int size, String sortBy, String sortDirection, String lang) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        if (query != null && !query.isEmpty()) {
            return PaginationUtil.getPage(() ->
                            repository.findNewsByQuery(query, lang, pageable),
                    mapper::toDto);
        }

        if (lang != null && !lang.isEmpty()) {
            return PaginationUtil.getPage(() ->
                            repository.findByNewsWithLang(lang, pageable),
                    mapper::toDto);
        }

        return PaginationUtil.getPage(() ->
                        repository.findAllNews(pageable),
                mapper::toDto);


    }
}
