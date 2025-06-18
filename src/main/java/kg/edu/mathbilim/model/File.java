package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.enums.FileType;
import kg.edu.mathbilim.enums.converter.FileTypeConverter;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.model.news.News;
import kg.edu.mathbilim.model.post.Post;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "filename", nullable = false)
    private String filename;

    @Size(max = 255)
    @NotNull
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Convert(converter = FileTypeConverter.class)
    @Column(name = "type_id", nullable = false)
    private FileType type;

    @Column(name = "size")
    private Long size;

    @Size(max = 255)
    @Column(name = "s3_link")
    private String s3Link;

    @OneToMany(mappedBy = "file")
    private List<Book> books = new ArrayList<>();

    @ManyToMany(mappedBy = "files")
    private List<Event> events = new ArrayList<>();

    @ManyToMany(mappedBy = "files")
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(mappedBy = "files")
    private List<News> news = new ArrayList<>();
}