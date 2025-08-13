package kg.edu.mathbilim.dto.event;

import kg.edu.mathbilim.dto.abstracts.DisplayContentDto;
import kg.edu.mathbilim.enums.ContentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DisplayEventDto extends DisplayContentDto {

    LocalDateTime startDate;
    LocalDateTime endDate;
    Integer typeId;
    String address;
    String url;
    Boolean isOffline;
    List<Long> organizationIds;

    public DisplayEventDto(Long id,
                           Long creatorId,
                           String creatorName,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt,
                           Long viewCount,
                           Long shareCount,
                           Long mainImageId,
                           Long approvedById,
                           ContentStatus status,
                           String title,
                           String content,
                           LocalDateTime startDate,
                           LocalDateTime endDate,
                           Integer typeId,
                           String address,
                           String url,
                           Boolean isOffline) {
        super(id, creatorId, createdAt, updatedAt, viewCount, shareCount,
                mainImageId, approvedById, status, title, content);
        this.startDate = startDate;
        this.endDate = endDate;
        this.typeId = typeId;
        this.address = address;
        this.url = url;
        this.isOffline = isOffline;
        this.organizationIds = null;
        this.setCreator(kg.edu.mathbilim.dto.user.UserDto.builder()
                .id(creatorId)
                .name(creatorName)
                .build());
    }


    public DisplayEventDto(Long id,
                           Long creatorId,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt,
                           Long viewCount,
                           Long shareCount,
                           Long mainImageId,
                           Long approvedById,
                           ContentStatus status,
                           String title,
                           String content,
                           LocalDateTime startDate,
                           LocalDateTime endDate,
                           Integer typeId,
                           String address,
                           String url,
                           Boolean isOffline,
                           List<Long> organizationIds) {
        super(id, creatorId, createdAt, updatedAt, viewCount, shareCount,
                mainImageId, approvedById, status, title, content);
        this.startDate = startDate;
        this.endDate = endDate;
        this.typeId = typeId;
        this.address = address;
        this.url = url;
        this.isOffline = isOffline;
        this.organizationIds = organizationIds;
    }

    public void setOrganizationIds(List<Long> organizationIds) {
        this.organizationIds = organizationIds;
    }

    public String getDescription() {
        StringBuilder desc = new StringBuilder();

        if (getContent() != null && !getContent().trim().isEmpty()) {
            String cleanContent = getContent().replaceAll("<[^>]*>", "").trim();
            if (cleanContent.length() > 150) {
                cleanContent = cleanContent.substring(0, 150) + "...";
            }
            desc.append(cleanContent);
        }

        if (startDate != null) {
            desc.append(" Дата проведения: ").append(startDate.toString());
        }

        if (isOffline != null && isOffline && address != null && !address.trim().isEmpty()) {
            desc.append(" Место: ").append(address);
        } else if (isOffline != null && !isOffline && url != null && !url.trim().isEmpty()) {
            desc.append(" Онлайн мероприятие");
        }

        return desc.toString();
    }


    public String getFormattedStartDate() {
        return startDate != null
                ? startDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", new Locale("ru")))
                : "";
    }

    public String getFormattedEndDate() {
        return endDate != null
                ? endDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", new Locale("ru")))
                : "";
    }

}