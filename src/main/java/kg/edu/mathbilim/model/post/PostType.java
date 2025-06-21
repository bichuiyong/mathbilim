package kg.edu.mathbilim.model.post;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "postType")
    private List<PostTypeTranslation> postTypeTranslations = new ArrayList<>();
}
