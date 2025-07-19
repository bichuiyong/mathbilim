package kg.edu.mathbilim.repository.abstracts;

import kg.edu.mathbilim.model.abstracts.BaseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
@NoRepositoryBean
public interface AbstractTypeRepository<T extends BaseType<?>> extends JpaRepository<T, Integer> {
    List<T> findAllByQuery(@Param("name") String name, @Param("lang") String lang);
}
