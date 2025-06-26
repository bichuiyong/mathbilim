package kg.edu.mathbilim.dto.abstracts;

import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.enums.ContentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class ContentDto extends AdminContentDto {
    @Builder.Default
    UserDto approvedBy = null;

    @Builder.Default
    ContentStatus status = ContentStatus.DRAFT;

    public void setApprovedById(Long approvedById) {
        if (approvedById != null) {
            this.approvedBy = UserDto.builder().id(approvedById).build();
        }
    }

    public Long getApprovedById() {
        return approvedBy != null ? approvedBy.getId() : null;
    }
}
