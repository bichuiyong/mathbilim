package kg.edu.mathbilim.model.abstracts;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@MappedSuperclass
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class TypeTranslation implements Serializable {

    @NotNull
    @Size(max = 100)
    @Column(name = "translation", nullable = false, length = 100)
    String translation;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class TranslationId implements Serializable {
        @Column(name = "type_id")
        Integer typeId;

        @NotNull
        @Size(min = 2, max = 2)
        @Column(name = "language_code", length = 2)
        String languageCode;
    }
}

