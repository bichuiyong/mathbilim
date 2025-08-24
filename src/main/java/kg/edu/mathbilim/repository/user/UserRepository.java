package kg.edu.mathbilim.repository.user;

import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    @Query("""
            select u from User u where 
            (LOWER(u.name) LIKE LOWER(CONCAT('%', :query, '%'))) OR 
            (LOWER(u.surname) LIKE LOWER(CONCAT('%', :query, '%'))) OR 
            (LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')))
            """)
    Page<User> findByQuery(@Param("query") String query, Pageable pageable);

    Boolean existsByEmail(String email);


    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(@Param("email") String email);

    Optional<User> findUserByResetPasswordToken(String resetPasswordToken);


    Optional<User> findByEmailVerificationToken(String resetPasswordToken);

    Optional<User> findByTelegramId(Long telegramId);

    boolean existsByTelegramId(Long telegramId);


    List<User> findAllBySubscribedTrueAndTelegramIdIsNotNull();

    @Query(value = """
   select 
          COALESCE((select count(*) from posts p where p.creator_id = :id and p.status_id = :status and p.deleted=false),0)
        + COALESCE((select count(*) from events e where e.creator_id = :id and e.status_id = :status and e.deleted=false),0)
        + COALESCE((select count(*) from organizations o where o.creator_id = :id and o.status_id = :status and o.deleted=false),0)
        + COALESCE((select count(*) from blogs b where b.creator_id = :id and b.status_id = :status and b.deleted=false),0)
        + COALESCE((select count(*) from books bk where bk.creator_id = :id and bk.status_id = :status and bk.deleted=false),0)
        """, nativeQuery = true)
    int countContentByStatus(@Param("id") Long id, @Param("status") int status);

    @Query(value = """
SELECT SUM(cnt) FROM (
    SELECT COUNT(*) AS cnt FROM posts p WHERE p.creator_id = :id and p.deleted=false
    UNION ALL
    SELECT COUNT(*) AS cnt FROM events e WHERE e.creator_id = :id and e.deleted=false
    UNION ALL
    SELECT COUNT(*) AS cnt FROM organizations o WHERE o.creator_id = :id and o.deleted=false
    UNION ALL
    SELECT COUNT(*) AS cnt FROM blogs b WHERE b.creator_id = :id and b.deleted=false
    UNION ALL
    SELECT COUNT(*) AS cnt FROM books bk WHERE bk.creator_id = :id and bk.deleted=false
    UNION ALL
    SELECT COUNT(*) AS cnt FROM news n WHERE n.creator_id = :id and n.deleted=false
    UNION ALL
    SELECT COUNT(*) AS cnt FROM olympiads o WHERE o.creator_id = :id and o.deleted=false
) t
""", nativeQuery = true)
    int totalContent(@Param("id") Long id);





}
