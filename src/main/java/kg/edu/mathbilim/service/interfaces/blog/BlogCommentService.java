package kg.edu.mathbilim.service.interfaces.blog;

import kg.edu.mathbilim.dto.blog.BlogCommentDto;
import kg.edu.mathbilim.model.blog.BlogComment;

import java.util.List;

public interface BlogCommentService {
    List<BlogCommentDto> getBlogCommentByBlogId(Long blogId);

    void createBlogComment(BlogCommentDto blogCommentDto);

    void deleteBlogComment(Long blogCommentId);

    void updateBlogComment(BlogCommentDto blogCommentDto);

    BlogComment findBlogCommentById(Long blogCommentId);
}
