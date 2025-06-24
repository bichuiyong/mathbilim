package kg.edu.mathbilim.repository.abstracts;

import kg.edu.mathbilim.model.abstracts.BaseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractTypeRepository<T extends BaseType<?>> extends JpaRepository<T, Integer> {
}
