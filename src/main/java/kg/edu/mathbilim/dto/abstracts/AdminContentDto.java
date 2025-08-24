package kg.edu.mathbilim.dto.abstracts;

import jakarta.validation.constraints.NotNull;
import kg.edu.mathbilim.dto.FileDto;
import kg.edu.mathbilim.dto.user.UserDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AdminContentDto {
    @Getter
    Long id;

    UserDto creator;

    @Builder.Default
    LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    LocalDateTime updatedAt = LocalDateTime.now();

    @NotNull
    @Builder.Default
    Long viewCount = 0L;

    @NotNull
    @Builder.Default
    Long shareCount = 0L;

    FileDto mainImage;


    @NotNull
    boolean deleted;


    public String getFormattedDate() {
        if (createdAt == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", new Locale("ru"));
        return createdAt.format(formatter);
    }

    public void setCreatorId(Long creatorId) {
        if (creatorId != null) {
            this.creator = UserDto.builder().id(creatorId).build();
        }
    }

    public void setMainImageId(Long mainImageId) {
        if (mainImageId != null) {
            this.mainImage = FileDto.builder().id(mainImageId).build();
        }
    }

    public Long getCreatorId() {
        return creator != null ? creator.getId() : null;
    }

    public Long getMainImageId() {
        return mainImage != null ? mainImage.getId() : null;
    }

}
