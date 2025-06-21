package kg.edu.mathbilim.model.blog;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.Comment;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "blog_comments")
public class BlogComment {
    @EmbeddedId
    private BlogCommentId id;

    @MapsId("commentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @MapsId("blogId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

}