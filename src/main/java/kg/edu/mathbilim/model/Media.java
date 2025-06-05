package kg.edu.mathbilim.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "media")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private String filePath;

    private Long fileSize;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private MediaType type;
}
