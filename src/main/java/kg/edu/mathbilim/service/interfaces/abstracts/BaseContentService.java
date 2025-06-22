package kg.edu.mathbilim.service.interfaces.abstracts;

import org.springframework.data.domain.Page;

public interface BaseContentService<D> {
    D getById(Long id);

    Page<D> getPage(String query, int page, int size, String sortBy, String sortDirection);

    void delete(Long id);

    boolean existsById(Long id);

    void incrementViewCount(Long id);

    void incrementShareCount(Long id);
}
