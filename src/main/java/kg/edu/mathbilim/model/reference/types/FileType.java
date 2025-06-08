package kg.edu.mathbilim.model.reference.types;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "file_types")
public class FileType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 100)
    @NotNull
    @Column(name = "mime_type", nullable = false, length = 100)
    private String mimeType;

    @Size(max = 20)
    @NotNull
    @Column(name = "extension", nullable = false, length = 20)
    private String extension;

}