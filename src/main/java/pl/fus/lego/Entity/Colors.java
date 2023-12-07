package pl.fus.lego.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Colors {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, length = 20)
    private String id;
    @Basic
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @Basic
    @Column(name = "rgb", nullable = false, length = 20)
    private String rgb;
    @Basic
    @Column(name = "is_trans", nullable = false, length = 20)
    private String isTrans;
    @OneToMany(mappedBy = "colorsByColorId")
    @JsonIgnore
    private Collection<Elements> elementsById;

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

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public String getIsTrans() {
        return isTrans;
    }

    public void setIsTrans(String isTrans) {
        this.isTrans = isTrans;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Colors colors = (Colors) o;

        if (id != null ? !id.equals(colors.id) : colors.id != null) return false;
        if (name != null ? !name.equals(colors.name) : colors.name != null) return false;
        if (rgb != null ? !rgb.equals(colors.rgb) : colors.rgb != null) return false;
        if (isTrans != null ? !isTrans.equals(colors.isTrans) : colors.isTrans != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (rgb != null ? rgb.hashCode() : 0);
        result = 31 * result + (isTrans != null ? isTrans.hashCode() : 0);
        return result;
    }

    public Collection<Elements> getElementsById() {
        return elementsById;
    }

    public void setElementsById(Collection<Elements> elementsById) {
        this.elementsById = elementsById;
    }
}
