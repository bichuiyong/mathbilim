package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("""
                SELECT DISTINCT b FROM Book b 
                LEFT JOIN b.authors a 
                WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :query, '%')) 
                   OR LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%')) 
                   OR LOWER(a.surname) LIKE LOWER(CONCAT('%', :query, '%')) 
                   OR LOWER(a.middleName) LIKE LOWER(CONCAT('%', :query, '%'))
            """)
    Page<Book> findByQuery(@Param("query") String query, Pageable pageable);
}
