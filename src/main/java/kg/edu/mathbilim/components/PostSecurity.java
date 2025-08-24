package kg.edu.mathbilim.components;

import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.repository.post.PostRepository;
import org.springframework.stereotype.Component;

@Component("postSecurity")
public class PostSecurity  extends ContentSecurity<Post, PostRepository> {
    public PostSecurity(PostRepository postRepository) {
        super(postRepository);
    }

}
