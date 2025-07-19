package kg.edu.mathbilim.model.abstracts;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class ContentTranslation {
    @Size(max = 500)
    @NotNull
    @Column(name = "title", nullable = false, length = 500)
    String title;

    @NotNull
    @Column(name = "content", nullable = false, length = Integer.MAX_VALUE)
    String content;
}
