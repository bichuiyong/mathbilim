package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "media_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String mimeType;

    private String extension;

    @OneToMany(mappedBy = "type")
    private List<Media> mediaList = new ArrayList<>();
}
