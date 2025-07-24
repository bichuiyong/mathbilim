package kg.edu.mathbilim.repository.event;

import kg.edu.mathbilim.dto.event.DisplayEventDto;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.model.blog.Blog;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.model.event.Event;
import kg.edu.mathbilim.repository.abstracts.BaseContentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends BaseContentRepository<Event> {
//    @Query("SELECT e FROM Event e WHERE " +
//            "LOWER(e.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(e.content) LIKE LOWER(CONCAT('%', :query, '%'))")
//    Page<Event> findByQuery(@Param("query") String query, Pageable pageable);

    @Modifying
    @Query("UPDATE Event b SET b.viewCount = b.viewCount + 1 WHERE b.id = :blogId")
    void incrementViewCount(@Param("blogId") Long blogId);

    @Modifying
    @Query("UPDATE Event b SET b.shareCount = b.shareCount + 1 WHERE b.id = :blogId")
    void incrementShareCount(@Param("blogId") Long blogId);

    @Query("""
                SELECT new kg.edu.mathbilim.dto.event.DisplayEventDto(
                    e.id,
                    e.creator.id,
                    e.createdAt, 
                    e.updatedAt, 
                    e.viewCount, 
                    e.shareCount,
                    e.mainImage.id,
                    e.approvedBy.id,
                    e.status,
                    et.title, 
                    et.content,
                    e.startDate,
                    e.endDate,
                    e.type.id,
                    e.address,
                    e.url,
                    e.isOffline
                )
                FROM Event e 
                JOIN e.eventTranslations et 
                WHERE e.id = :eventId 
                AND et.id.languageCode = :languageCode
            """)
    Optional<DisplayEventDto> findDisplayEventById(@Param("eventId") Long eventId,
                                                   @Param("languageCode") String languageCode);

    @Query("""
                SELECT o.id FROM Event e 
                JOIN e.organizations o 
                WHERE e.id = :eventId
            """)
    List<Long> findOrganizationIdsByEventId(@Param("eventId") Long eventId);

    @Query("""
            SELECT DISTINCT p FROM Event p
            JOIN p.eventTranslations t
            WHERE p.status = :contentStatus
            ORDER BY p.createdAt DESC
            """)
    Page<Event> findEventsByStatus(ContentStatus status, Pageable pageable);

    @Query("""
            SELECT DISTINCT p FROM Event p
            JOIN p.eventTranslations t
            WHERE p.status = :contentStatus
                        AND
            LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
            ORDER BY p.createdAt DESC
            """)
    Page<Event> getEventsByStatusWithQuery(ContentStatus contentStatus,
                                           String query,
                                           Pageable pageable);


    @Query("""
                SELECT DISTINCT p FROM Event p
                JOIN p.eventTranslations t
                WHERE p.status = :contentStatus
                  AND p.creator.id = :userId
                  AND LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
                ORDER BY p.createdAt DESC
            """)
    Page<Event> getEventsByCreatorAndStatusAndQuery(@Param("contentStatus") ContentStatus contentStatus,
                                                    @Param("userId") Long userId,
                                                    @Param("query") String query,
                                                    Pageable pageable);


    @Query("""
                SELECT DISTINCT e FROM Event e
                JOIN e.eventTranslations t
                WHERE e.creator.id = :userId
                  AND LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))
                ORDER BY e.createdAt DESC
            """)
    Page<Event> getEventsWithQuery(@Param("query") String query,
                                   @Param("userId") Long userId,
                                   Pageable pageable);


    Long countByStatus(ContentStatus status);

    @Query("""
            SELECT DISTINCT e FROM Event e
               WHERE e.status = :contentStatus
            """)
    Page<Event> getEventsByStatus(ContentStatus contentStatus, Pageable pageable);


    @Query("""
                SELECT DISTINCT e FROM Event e
                WHERE e.status = :contentStatus
                  AND e.creator.id = :userId
                ORDER BY e.createdAt DESC
            """)
    Page<Event> getEventsByStatusAndCreator(@Param("contentStatus") ContentStatus contentStatus,
                                            @Param("userId") Long userId,
                                            Pageable pageable);
}
