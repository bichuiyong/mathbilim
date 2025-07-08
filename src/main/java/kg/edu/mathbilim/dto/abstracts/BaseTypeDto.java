package kg.edu.mathbilim.dto.abstracts;

import jakarta.persistence.*;
import kg.edu.mathbilim.validation.annotation.AllTranslationsRequired;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseTypeDto<T> {

    Integer id;

    @AllTranslationsRequired
    List<T> translations = new ArrayList<>();
}
