package pl.fus.lego.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Themes {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, length = 20)
    private String id;
    @Basic
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @Basic
    @Column(name = "parent_id", nullable = true, length = 20, insertable=false, updatable=false)
    private String parentId;
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @JsonIgnore
    private Themes themesByParentId;
    @OneToMany(mappedBy = "themesByParentId")
    private Collection<Themes> themesById;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Themes themes = (Themes) o;

        if (id != null ? !id.equals(themes.id) : themes.id != null) return false;
        if (name != null ? !name.equals(themes.name) : themes.name != null) return false;
        if (parentId != null ? !parentId.equals(themes.parentId) : themes.parentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        return result;
    }

    public Themes getThemesByParentId() {
        return themesByParentId;
    }

    public void setThemesByParentId(Themes themesByParentId) {
        this.themesByParentId = themesByParentId;
    }

    public Collection<Themes> getThemesById() {
        return themesById;
    }

    public void setThemesById(Collection<Themes> themesById) {
        this.themesById = themesById;
    }
}
