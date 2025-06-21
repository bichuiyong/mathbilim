package kg.edu.mathbilim.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class TranslationContent {
    @Size(max = 500)
    @NotNull
    @Column(name = "title", nullable = false, length = 500)
    protected String title;

    @NotNull
    @Column(name = "content", nullable = false, length = Integer.MAX_VALUE)
    protected String content;
}
