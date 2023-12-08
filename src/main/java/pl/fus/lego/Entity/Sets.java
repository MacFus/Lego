package pl.fus.lego.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Sets {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "set_num", nullable = false, length = 20)
    private String setNum;
    @Basic
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @Basic
    @Column(name = "year", nullable = false, length = 20)
    private Integer year;
    @Basic
    @Column(name = "theme_id", nullable = false)
    private Integer themeId;
    @Basic
    @Column(name = "num_parts", nullable = true)
    private Integer numParts;
    @Basic
    @Column(name = "img_url", nullable = true, length = 255)
    private String imgUrl;

    public String getSetNum() {
        return setNum;
    }
}
