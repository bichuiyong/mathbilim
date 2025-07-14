package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.Language;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.model.Book;
import kg.edu.mathbilim.model.post.Post;
import kg.edu.mathbilim.repository.abstracts.BaseContentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BaseContentRepository<Book> {
    @Override
    @Query("""
                SELECT DISTINCT b FROM Book b where
                             (LOWER(b.name) LIKE LOWER(CONCAT('%', :query, '%')))
            """)
    Page<Book> findByQuery(@Param("query") String query, Pageable pageable);

    @Query("""
    SELECT b FROM Book b
    WHERE b.status = :status
      AND LOWER(b.name) LIKE LOWER(:query)
""")
    Page<Book> searchByStatusAndQuery(
            @Param("status") ContentStatus status,
            @Param("query") String query,
            Pageable pageable
    );


    @Modifying
    @Query("UPDATE Book b SET b.viewCount = b.viewCount + 1 WHERE b.id = :blogId")
    void incrementViewCount(@Param("blogId") Long blogId);

    @Modifying
    @Query("UPDATE Book b SET b.shareCount = b.shareCount + 1 WHERE b.id = :blogId")
    void incrementShareCount(@Param("blogId") Long blogId);

    Optional<Book> findByIdAndCreatorId(Long id, Long user);

    @Query("""
            SELECT DISTINCT b FROM Book b
               WHERE b.status = :contentStatus
            """)
    Page<Book> getBooksByStatus(ContentStatus contentStatus, Pageable pageable);

    @Query("""
           select b from Book b 
           where b.status=:status   
            ORDER BY b.createdAt DESC      
           """)
    Page<Book> findAllBooksByLanguage(ContentStatus status, Pageable pageable);

    @Query("""
           select b from Book b 
           where b.status=:status 
                       AND
            LOWER(b.name) LIKE LOWER(CONCAT('%', :query, '%'))
            ORDER BY b.createdAt DESC          
           """)
    Page<Book> findAllBooksByLanguageAndQuery(String query,ContentStatus status,  Pageable pageable);

    @Query("""
           select b from Book b 
           where b.status=:status 
                       AND
            b.category.id=:categoryId
            ORDER BY b.createdAt DESC          
           """)
    Page<Book> findAllBooksByCategory(Long categoryId,ContentStatus status,  Pageable pageable);
}






