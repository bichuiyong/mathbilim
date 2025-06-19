package kg.edu.mathbilim.service.interfaces.blog;

import kg.edu.mathbilim.model.blog.Blog;

public interface BlogService {
    Blog findBlogById(Long id);

    Boolean existsBlogById(Long id);
}
