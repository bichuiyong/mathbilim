package kg.edu.mathbilim.service.interfaces.blog;

import kg.edu.mathbilim.dto.blog.BlogCommentDto;

import java.util.List;

public interface BlogCommentService {
    List<BlogCommentDto> getBlogCommentByBlogId(Long blogId);

    void createBlogComment(BlogCommentDto blogCommentDto);
}
