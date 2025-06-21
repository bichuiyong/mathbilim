package kg.edu.mathbilim.model.olympiad;

import jakarta.persistence.*;

@Entity
@Table(name = "contact_type")
public class ContactType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String type;

}
