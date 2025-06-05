package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "content_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

// Код ниже вызывает ошибки
//    @OneToMany(mappedBy = "status")
//    private List<ContentStatus> contentStatusList = new ArrayList<>();
}
