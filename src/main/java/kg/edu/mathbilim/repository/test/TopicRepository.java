package kg.edu.mathbilim.repository.test;

import kg.edu.mathbilim.model.test.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
