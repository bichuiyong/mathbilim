package kg.edu.mathbilim.dto;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.embedded.ContentBlock;
import lombok.*;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentDto {
    private Long id;

    private String title;

    private String slug;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder.Default
    private Long viewCount = 0L;

    @Builder.Default
    private Long shareCount = 0L;

    private List<ContentBlockDto> contentBlocks;

    private Map<String, Object> metadata;

    private UserDto author;

    private UserDto approvedBy;

    private CategoryDto category;

    private ContentStatusDto status;

    private ContentTypeDto type;
}
