package pl.fus.lego.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_sets", schema = "lego", catalog = "")
public class UserSets {

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "set_num", nullable = false, length = 60)
    private String setNum;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public UserSets( String setNum, int userId, int quantity) {
        this.userId = userId;
        this.setNum = setNum;
        this.quantity = quantity;
    }

    public UserSets() {

    }
}
