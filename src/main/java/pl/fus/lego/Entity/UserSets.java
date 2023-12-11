package pl.fus.lego.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_sets", schema = "lego", catalog = "")
@IdClass(UserSetsPK.class)
public class UserSets {
    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Id
    @Column(name = "set_num", nullable = false, length = 60)
    private String setNum;
    @Basic
    @Column(name = "set_count", nullable = true)
    private Integer setCount;
}
