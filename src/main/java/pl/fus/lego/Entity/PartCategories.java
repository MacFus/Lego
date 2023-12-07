package pl.fus.lego.Entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "part_categories", schema = "lego", catalog = "")
public class PartCategories {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, length = 20)
    private String id;
    @Basic
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @OneToMany(mappedBy = "partCategoriesByPartCatId")
    private Collection<Parts> partsById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartCategories that = (PartCategories) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public Collection<Parts> getPartsById() {
        return partsById;
    }

    public void setPartsById(Collection<Parts> partsById) {
        this.partsById = partsById;
    }
}
