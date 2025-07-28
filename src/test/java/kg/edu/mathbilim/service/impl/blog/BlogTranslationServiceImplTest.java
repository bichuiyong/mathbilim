package kg.edu.mathbilim.service.impl.blog;

import kg.edu.mathbilim.dto.blog.BlogTranslationDto;
import kg.edu.mathbilim.mapper.blog.BlogTranslationMapper;
import kg.edu.mathbilim.model.blog.BlogTranslationId;
import kg.edu.mathbilim.repository.blog.BlogTranslationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlogTranslationServiceImplTest {

    private BlogTranslationRepository repository;
    private BlogTranslationMapper mapper;
    private BlogTranslationServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(BlogTranslationRepository.class);
        mapper = mock(BlogTranslationMapper.class);
        service = new BlogTranslationServiceImpl(repository, mapper);
    }

    @Test
    void testCreateTranslationId() {
        Long blogId = 123L;
        String languageCode = "en";

        BlogTranslationId id = service.createTranslationId(blogId, languageCode);

        assertNotNull(id);
        assertEquals(blogId, id.getBlogId());
        assertEquals(languageCode, id.getLanguageCode());
    }

    @Test
    void testSetEntityId() {
        BlogTranslationDto dto = new BlogTranslationDto();
        Long blogId = 456L;

        service.setEntityId(dto, blogId);

        assertEquals(blogId, dto.getBlogId());
    }

    @Test
    void testGetEntityName() {
        assertEquals("blog", service.getEntityName());
    }

    @Test
    void testGetEntityIdFromDto() {
        BlogTranslationDto dto = new BlogTranslationDto();
        dto.setBlogId(789L);

        Long result = service.getEntityIdFromDto(dto);

        assertEquals(789L, result);
    }

    @Test
    void testDeleteAllTranslationsByEntityIdImpl() {
        Long blogId = 321L;

        service.deleteAllTranslationsByEntityIdImpl(blogId);

        verify(repository, times(1)).deleteByBlogId(blogId);
    }
}
