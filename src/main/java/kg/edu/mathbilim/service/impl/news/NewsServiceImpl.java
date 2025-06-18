package kg.edu.mathbilim.service.impl.news;

import kg.edu.mathbilim.dto.news.CreateNewsDto;
import kg.edu.mathbilim.dto.news.NewsDto;
import kg.edu.mathbilim.dto.news.NewsTranslationDto;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.mapper.user.UserMapper;
import kg.edu.mathbilim.mapper.news.NewsMapper;
import kg.edu.mathbilim.mapper.news.NewsTranslationMapper;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.news.News;
import kg.edu.mathbilim.model.news.NewsTranslation;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.repository.news.NewsRepository;
import kg.edu.mathbilim.service.interfaces.FileService;
import kg.edu.mathbilim.service.interfaces.UserService;
import kg.edu.mathbilim.service.interfaces.news.NewsService;
import kg.edu.mathbilim.service.interfaces.news.NewsTranslationService;
import kg.edu.mathbilim.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final UserService userService;
    private final NewsTranslationService newsTranslationService;
    private final NewsTranslationMapper newsTranslationMapper;
    private final FileService fileService;
    private final UserMapper userMapper;

    @Override
    public NewsDto getNewsById(Long id) {
        return newsMapper.toDto(
                newsRepository.findById(id).orElseThrow(
                        ()-> new NoSuchElementException("No News found with id: " + id))
        );
    }

    @Override
    public Page<NewsDto> getNewsPage(int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PaginationUtil.createPageableWithSort(page, size, sortBy, sortDirection);
        return PaginationUtil.getPage(() -> newsRepository.findAll(pageable), newsMapper::toDto);
    }


    @Override
    public void deleteById(UserDto userDto, Long id) {
        News news = newsRepository.findByIdAndUserId(id, userDto.getId()).orElseThrow(
                () -> new NoSuchElementException("No News found with id: " + id)
        );
        newsRepository.delete(news);
        log.info("Deleted News: {}", news);
    }

    @Override
    public NewsDto createNews(CreateNewsDto createNewsDto) {
        NewsDto newsDto1 = createNewsDto.getNews();
        newsDto1.setUser(userService.getAuthUser());

        News news = newsMapper.toEntity(newsDto1);
        News savedNews = newsRepository.save(news);
        Long id = savedNews.getId();

        List<NewsTranslationDto> translationDtos = newsDto1.getNewsTranslationDto();
        setPostTranslations(translationDtos, id);

        MultipartFile mainImage = createNewsDto.getImage() != null && createNewsDto.getImage().isEmpty() ? null : createNewsDto.getImage();
        uploadMainImage(mainImage, savedNews);

        MultipartFile[] attachments = createNewsDto.getAttachments() == null ? new MultipartFile[0] : createNewsDto.getAttachments();
        uploadPostFiles(attachments, savedNews);

        newsRepository.saveAndFlush(news);

        log.info("Created post: {}", savedNews);
        return newsMapper.toDto(savedNews);
    }

    @Override
    @Transactional
    public NewsDto updateNews(CreateNewsDto dto, Long id) {
        News existing = newsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("News not found with ID: " + id));

        NewsDto incomingDto = dto.getNews();

        existing.setUser(userMapper.toEntity(userService.getAuthUser()));
        existing.setUpdatedAt(Instant.now());

        setPostTranslations(incomingDto.getNewsTranslationDto(), id);

        MultipartFile newImage = dto.getImage();
        if (newImage != null && !newImage.isEmpty()) {
            uploadMainImage(newImage, existing);
        }

        MultipartFile[] newAttachments = dto.getAttachments() != null ? dto.getAttachments() : new MultipartFile[0];
        if (newAttachments.length > 0) {
            uploadPostFiles(newAttachments, existing);
        }

        News updated = newsRepository.saveAndFlush(existing);
        log.info("Updated post: {}", updated);
        return newsMapper.toDto(updated);
    }

    private void updateNewsTranslations(News news, List<NewsTranslationDto> translationDtos) {
        news.getNewsTranslations().clear();
        List<NewsTranslation> updated = translationDtos.stream()
                .map(dto -> newsTranslationMapper.toEntity(dto))
                .peek(t -> t.setNews(news))
                .toList();

        news.getNewsTranslations().addAll(updated);
    }



    private void setPostTranslations(List<NewsTranslationDto> translations, Long postId) {
        Set<NewsTranslationDto> filledTranslations = translations.stream()
                .filter(translation ->
                        translation.getTitle() != null && !translation.getTitle().trim().isEmpty() &&
                                translation.getContent() != null && !translation.getContent().trim().isEmpty())
                .collect(Collectors.toCollection(LinkedHashSet::new));


        newsTranslationService.saveTranslations(postId, filledTranslations);
    }
    private void uploadMainImage(MultipartFile mainImage, News news) {
        if (mainImage != null && !mainImage.isEmpty()) {
            File mainImageFile = fileService.uploadFileReturnEntity(
                    mainImage,
                    "events/" + news.getId() + "/main"
            );
            news.setMainImage(mainImageFile);
            log.info("Uploaded main image for post {}", news.getId());
        }
    }

    private void uploadPostFiles(MultipartFile[] attachments, News savedNews) {
        if (attachments.length > 0) {
            List<File> uploadedFiles = fileService.uploadFilesForNews(attachments, savedNews);
            if (!uploadedFiles.isEmpty()) {
                savedNews.setFiles(uploadedFiles);
                newsRepository.saveAndFlush(savedNews);
                log.info("Uploaded {} files for post {}", uploadedFiles.size(), savedNews.getId());
            }
        }
    }



}
