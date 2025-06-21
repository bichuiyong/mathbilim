package kg.edu.mathbilim.model.news;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.model.File;
import kg.edu.mathbilim.model.abstracts.Content;
import kg.edu.mathbilim.model.user.User;
import lombok.*;
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
public class News extends Content {

    @Override
    @Transient
    public User getApprovedBy() {
        return null;
    }

    @Override
    @Transient
    public void setApprovedBy(User approvedBy) {
        // Игнорируем, так как поля нет в таблице
    }

    @Override
    @Transient
    public ContentStatus getStatus() {
        return null;
    }

    @Override
    @Transient
    public void setStatus(ContentStatus status) {
        // Игнорируем, так как поля нет в таблице
    }

    @ManyToMany
    @JoinTable(name = "news_files",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private List<File> newsFiles = new ArrayList<>();

    @OneToMany(mappedBy = "news")
    private List<NewsTranslation> newsTranslations = new ArrayList<>();

}