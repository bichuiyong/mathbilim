package kg.edu.mathbilim.model.abstracts;

import jakarta.persistence.*;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class TypeTranslation<E extends BaseType<?>> implements Serializable {

    @EmbeddedId
    private TranslationId id;

    @NotNull
    @Size(max = 100)
    @Column(name = "translation", nullable = false, length = 100)
    private String translation;

    @MapsId("typeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    E parent;

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

