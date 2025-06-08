package kg.edu.mathbilim.repository.reference.status;

import kg.edu.mathbilim.model.reference.status.TestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestStatusRepository extends JpaRepository<TestStatus, Integer> {
}
