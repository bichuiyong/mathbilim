package kg.edu.mathbilim.components;

import kg.edu.mathbilim.model.abstracts.AdminContent;
import kg.edu.mathbilim.repository.abstracts.BaseContentRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public abstract class ContentSecurity<
        T extends AdminContent,
        E extends BaseContentRepository<T>
        > {
    private final E repository;

    public boolean isOwner(Long newsId, String username) {
        return repository.findById(newsId)
                .map(news -> news.getCreator().getEmail().equals(username))
                .orElse(false);
    }

}
