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
    @Column(name = "id",
            nullable = false)
    private Integer id;

    @Column(name = "name",
            unique = true,
            nullable = false)
    private String name;

    @Column(name = "mime_type",
            nullable = false)
    private String mimeType;

    @Column(name = "extension",
            nullable = false)
    private String extension;
}
