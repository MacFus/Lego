package pl.fus.lego.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserSetsPK implements Serializable {
    @Column(name = "user_id", nullable = false)
    @Id
    private int userId;
    @Column(name = "set_num", nullable = false)
    @Id
    private String setNum;

}
