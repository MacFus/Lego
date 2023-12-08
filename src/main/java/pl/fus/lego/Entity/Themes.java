package pl.fus.lego.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
@Entity
public class Themes {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @Basic
    @Column(name = "parent_id", nullable = true, insertable=false, updatable=false)
    private Integer parentId;
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Themes themesByParentId;
    @OneToMany(mappedBy = "themesByParentId")
    private Collection<Themes> themesById;
}
