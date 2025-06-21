package kg.edu.mathbilim.model.post;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.abstracts.Content;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
@SuperBuilder
public class Post extends Content {
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "type_id")
    private PostType type;

    @ManyToMany
    @JoinTable(name = "post_files",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private List<File> postFiles = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostTranslation> postTranslations = new ArrayList<>();

}