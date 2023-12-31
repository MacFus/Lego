package pl.fus.lego.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
@Entity
public class Themes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "parent_id", insertable = false, updatable = false)
    private Integer parentId;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Themes parentTheme;

    @OneToMany(mappedBy = "parentTheme")
    private Collection<Themes> childThemes;
}