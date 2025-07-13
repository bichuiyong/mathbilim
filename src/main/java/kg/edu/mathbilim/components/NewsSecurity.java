package kg.edu.mathbilim.components;

import kg.edu.mathbilim.repository.news.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("newsSecurity")
public class NewsSecurity {

    @Autowired
    private NewsRepository newsRepository;

    public boolean isOwner(Long newsId, String username) {
        return newsRepository.findById(newsId)
                .map(news -> news.getCreator().getEmail().equals(username))
                .orElse(false);
    }
}
