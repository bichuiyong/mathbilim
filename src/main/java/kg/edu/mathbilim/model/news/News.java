package kg.edu.mathbilim.model.news;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.abstracts.AdminContent;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "news")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class News extends AdminContent {
    @ManyToMany
    @JoinTable(name = "news_files",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    List<File> newsFiles = new ArrayList<>();

    @OneToMany(mappedBy = "news")
    List<NewsTranslation> newsTranslations = new ArrayList<>();

    @Override
    public void setStatus(ContentStatus status) {

    }
}