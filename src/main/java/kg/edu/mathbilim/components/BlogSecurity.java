package kg.edu.mathbilim.components;

import kg.edu.mathbilim.model.blog.Blog;
import kg.edu.mathbilim.repository.blog.BlogRepository;
import org.springframework.stereotype.Component;

@Component("blogSecurity")
public class BlogSecurity extends ContentSecurity<Blog, BlogRepository> {
   public BlogSecurity(BlogRepository blogRepository) {
       super(blogRepository);
   }
}
