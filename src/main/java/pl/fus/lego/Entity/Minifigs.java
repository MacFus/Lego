package pl.fus.lego.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Minifigs {
    @Id
    @Column(name = "fig_num", nullable = false, length = 20)
    private String figNum;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "num_parts", nullable = false, length = 20)
    private String numParts;
    @Basic
    @Column(name = "img_url", nullable = false, length = 255)
    private String imgUrl;
}
