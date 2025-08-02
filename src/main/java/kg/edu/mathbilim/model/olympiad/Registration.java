package kg.edu.mathbilim.model.olympiad;

import jakarta.persistence.*;
import kg.edu.mathbilim.model.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
@Entity
@Table(name = "registration")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "olympiad_stage_id")
    private OlympiadStage olympiadStage;

    @Column(length = 100)
    private String email;

    @Column(length = 200)
    private String region;

    @Column(length = 100)
    private String district;

    @Column(name = "full_name", length = 300)
    private String fullName;

    @Column(name = "phone_number", length = 200)
    private String phoneNumber;

    @Column(length = 100)
    private String school;

    @Column(length = 100)
    private String telegram;

    @Column(length = 100)
    private String classNumber;

    @Column(length = 100)
    private String locality;

    @Column(name = "class_teacher_full_name", length = 100)
    private String classTeacherFullName;

    @Column(name = "parent_full_name", length = 100)
    private String parentFullName;

    @Column(name = "parent_phone_number", length = 100)
    private String parentPhoneNumber;

    @Column(name = "parent_email", length = 100)
    private String parentEmail;

    private LocalDateTime created;
}
