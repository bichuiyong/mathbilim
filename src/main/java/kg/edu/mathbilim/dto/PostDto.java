package kg.edu.mathbilim.dto;

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

    private PostTypeDto type;

    private String title;

    private String slug;

    private Map<String, Object> content;

    private Instant createdAt;

    private Instant updatedAt;

    private Long viewCount;

    private Long shareCount;

    private ContentStatusDto status;

    private UserDto user;

    private UserDto approvedBy;

}