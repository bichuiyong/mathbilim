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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends BaseContentRepository<Event> {

    @Modifying
    @Query("UPDATE Event b SET b.viewCount = b.viewCount + 1 WHERE b.id = :blogId  AND b.deleted = false")
    void incrementViewCount(@Param("blogId") Long blogId);

    @Modifying
    @Query("UPDATE Event b SET b.shareCount = b.shareCount + 1 WHERE b.id = :blogId  AND b.deleted = false")
    void incrementShareCount(@Param("blogId") Long blogId);

    @Query("""
 SELECT new kg.edu.mathbilim.dto.event.DisplayEventDto(
     e.id,
     e.creator.id,
     e.creator.name,
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
    AND e.deleted = false
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
            WHERE p.status = :status   AND p.deleted = false
            ORDER BY p.createdAt DESC
            
            """)
    Page<Event> findEventsByStatus(ContentStatus status, Pageable pageable);

    @Query("""
            SELECT DISTINCT p FROM Event p
            JOIN p.eventTranslations t
            WHERE p.status = :contentStatus
                     AND p.deleted = false AND
            LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) and 
                        p.deleted = false
            """)
    Page<Event> getEventsByStatusWithQuery(ContentStatus contentStatus,
                                           String query,
                                           Pageable pageable);


    @Query("""
            SELECT DISTINCT p FROM Event p
            WHERE p.status = :contentStatus
                     and p.creator.id = :creatorId
                     AND p.deleted = false
            ORDER BY p.createdAt DESC
            """)
    Page<Event> getEventsByCreatorId(ContentStatus contentStatus,
                                           Long creatorId,
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
                AND e.deleted = false
            """)
    Page<Event> getEventsByStatus(ContentStatus contentStatus, Pageable pageable);


    @Query("""
                SELECT DISTINCT e FROM Event e
                WHERE e.status = :contentStatus
                  AND e.creator.id = :userId
                   AND e.deleted = false
                ORDER BY e.createdAt DESC
            """)
    Page<Event> getEventsByStatusAndCreator(@Param("contentStatus") ContentStatus contentStatus,
                                            @Param("userId") Long userId,
                                            Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.isOffline = :offline  AND e.deleted = false")
    Page<Event> getAllEventsByType(@Param("offline") Boolean offline, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Event e SET e.deleted = true WHERE e.id = :eventId")
    void deleteContentById(@Param("eventId") Long eventId);

}
