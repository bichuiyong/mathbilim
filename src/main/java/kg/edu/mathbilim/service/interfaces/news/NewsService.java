package kg.edu.mathbilim.service.interfaces.news;

import kg.edu.mathbilim.dto.news.CreateNewsDto;
import kg.edu.mathbilim.dto.news.NewsDto;
import kg.edu.mathbilim.dto.user.UserDto;
import org.springframework.data.domain.Page;

public interface NewsService {
    NewsDto getNewsById(Long id);

    Page<NewsDto> getNewsPage(int page, int size, String sortBy, String sortDirection);

    void deleteById(UserDto userDto, Long id);

    NewsDto createNews(CreateNewsDto newsDto);

    NewsDto updateNews(CreateNewsDto newsDto, Long id);
}
