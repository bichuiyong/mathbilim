package kg.edu.mathbilim.components;

import kg.edu.mathbilim.model.news.News;
import kg.edu.mathbilim.repository.news.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("newsSecurity")
public class NewsSecurity extends ContentSecurity<News, NewsRepository> {
    public NewsSecurity(NewsRepository repository) {
        super(repository);
    }

}
