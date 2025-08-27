package kg.edu.mathbilim.telegram.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NotificationData {
    private Long id;
    private String message;
    private String title;
    private Long mainImageId;
    private String description;
    private Long contentId;
}
