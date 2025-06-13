package kg.edu.mathbilim.model.reference.post_type;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

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
    private Set<PostTypeTranslation> postTypeTranslations = new LinkedHashSet<>();
}
