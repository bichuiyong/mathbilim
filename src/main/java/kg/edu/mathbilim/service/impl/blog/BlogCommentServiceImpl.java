package kg.edu.mathbilim.service.impl.blog;

import kg.edu.mathbilim.dto.blog.BlogCommentDto;
import kg.edu.mathbilim.mapper.blog.BlogCommentMapper;
import kg.edu.mathbilim.repository.blog.BlogCommentRepository;
import kg.edu.mathbilim.service.interfaces.blog.BlogCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogCommentServiceImpl implements BlogCommentService {
    private final BlogCommentRepository blogCommentRepository;
    private final BlogCommentMapper blogCommentMapper;

    @Override
    public List<BlogCommentDto> getBlogCommentByBlogId(Long blogId) {
        return blogCommentRepository.findAllByBlog_Id(blogId).stream()
                .map(blogCommentMapper::toDto)
                .toList();
    }

    @Override
    public void createBlogComment(BlogCommentDto blogCommentDto) {
        blogCommentRepository.save(blogCommentMapper.toEntity(blogCommentDto));
    }
}
