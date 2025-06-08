package kg.edu.mathbilim.repository;

import kg.edu.mathbilim.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

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

}
