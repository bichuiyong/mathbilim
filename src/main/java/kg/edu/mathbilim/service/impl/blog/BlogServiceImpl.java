package kg.edu.mathbilim.service.impl.blog;

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
}
