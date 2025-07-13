package kg.edu.mathbilim.model.abstracts;

import jakarta.persistence.*;
import kg.edu.mathbilim.enums.ContentStatus;
import kg.edu.mathbilim.enums.converter.ContentStatusConverter;
import kg.edu.mathbilim.model.user.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@MappedSuperclass
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class Content extends AdminContent{

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "approved_by")
    User approvedBy;

    @Convert(converter = ContentStatusConverter.class)
    @Column(name = "status_id", nullable = false)
    ContentStatus status;

}
