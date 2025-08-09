package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.edu.mathbilim.enums.FileType;
import kg.edu.mathbilim.enums.converter.FileTypeConverter;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.model.news.News;
import kg.edu.mathbilim.model.olympiad.Olympiad;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.model.test.Test;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "files")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "filename", nullable = false)
    String filename;

    @Size(max = 255)
    @NotNull
    @Column(name = "file_path", nullable = false)
    String filePath;

    @Convert(converter = FileTypeConverter.class)
    @Column(name = "type_id", nullable = false)
    FileType type;

    @Column(name = "size")
    Long size;

    @Size(max = 255)
    @Column(name = "s3_link")
    String s3Link;

    @OneToMany(mappedBy = "file")
    List<Book> books = new ArrayList<>();

    @ManyToMany(mappedBy = "eventFiles")
    List<Event> events = new ArrayList<>();

    @ManyToMany(mappedBy = "postFiles")
    List<Post> posts = new ArrayList<>();

    @ManyToMany(mappedBy = "newsFiles")
    List<News> news = new ArrayList<>();

    @OneToMany(mappedBy = "image")
    List<Olympiad> olympiads = new ArrayList<>();

    @OneToMany(mappedBy = "file")
    List<Test> tests = new ArrayList<>();
}