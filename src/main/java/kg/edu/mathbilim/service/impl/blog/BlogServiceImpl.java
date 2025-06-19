package kg.edu.mathbilim.service.impl.blog;

import kg.edu.mathbilim.exception.nsee.BlogNotFoundException;
import kg.edu.mathbilim.model.blog.Blog;
import kg.edu.mathbilim.repository.blog.BlogRepository;
import kg.edu.mathbilim.service.interfaces.blog.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;

    @Override
    public Blog findBlogById(Long id) {
        return blogRepository.findById(id).orElseThrow(BlogNotFoundException::new);
    }

    @Override
    public Boolean existsBlogById(Long id) {
        return blogRepository.existsById(id);
    }
}
