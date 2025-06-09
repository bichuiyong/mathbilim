package kg.edu.mathbilim.dto;

import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.PostType;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Long id;

    private PostType type;

    private String title;

    private String slug;

    private Map<String, Object> content;

    private Instant createdAt;

    private Instant updatedAt;

    private Long viewCount;

    private Long shareCount;

    private ContentStatus status;

    private UserDto user;

    private UserDto approvedBy;

}