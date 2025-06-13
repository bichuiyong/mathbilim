package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("""
                SELECT DISTINCT b FROM Book b 
            """)
    Page<Book> findByQuery(@Param("query") String query, Pageable pageable);

    Optional<Book> findByIdAndUserId(Long id, Long user);
}
