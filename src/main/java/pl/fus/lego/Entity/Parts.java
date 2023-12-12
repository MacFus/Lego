package pl.fus.lego.Entity;


import jakarta.persistence.Entity;

import jakarta.persistence.*;

import java.util.Collection;
@Entity
public class Parts {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "part_num", nullable = false, length = 20)
    private String partNum;
    @Basic
    @Column(name = "name", nullable = false, length = 300)
    private String name;
    @Basic
    @Column(name = "part_cat_id", nullable = false, length = 20, insertable=false, updatable=false)
    private String partCatId;
    @Basic
    @Column(name = "part_material", nullable = false, length = 20)
    private String partMaterial;
    @OneToMany(mappedBy = "partsByPartNum")
    private Collection<Elements> elementsByPartNum;
    @OneToMany(mappedBy = "partsByChildPartNum")
    private Collection<PartRelationships> partRelationshipsByPartNum;
    @OneToMany(mappedBy = "partsByParentPartNum")
    private Collection<PartRelationships> partRelationshipsByPartNum_0;
    @ManyToOne
    @JoinColumn(name = "part_cat_id", referencedColumnName = "id", nullable = false)
    private PartCategories partCategoriesByPartCatId;

    public String getPartNum() {
        return partNum;
    }

    public void setPartNum(String partNum) {
        this.partNum = partNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartCatId() {
        return partCatId;
    }

    public void setPartCatId(String partCatId) {
        this.partCatId = partCatId;
    }

    public String getPartMaterial() {
        return partMaterial;
    }

    public void setPartMaterial(String partMaterial) {
        this.partMaterial = partMaterial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parts parts = (Parts) o;

        if (partNum != null ? !partNum.equals(parts.partNum) : parts.partNum != null) return false;
        if (name != null ? !name.equals(parts.name) : parts.name != null) return false;
        if (partCatId != null ? !partCatId.equals(parts.partCatId) : parts.partCatId != null) return false;
        if (partMaterial != null ? !partMaterial.equals(parts.partMaterial) : parts.partMaterial != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = partNum != null ? partNum.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (partCatId != null ? partCatId.hashCode() : 0);
        result = 31 * result + (partMaterial != null ? partMaterial.hashCode() : 0);
        return result;
    }

    public Collection<Elements> getElementsByPartNum() {
        return elementsByPartNum;
    }

    public void setElementsByPartNum(Collection<Elements> elementsByPartNum) {
        this.elementsByPartNum = elementsByPartNum;
    }

    public Collection<PartRelationships> getPartRelationshipsByPartNum() {
        return partRelationshipsByPartNum;
    }

    public void setPartRelationshipsByPartNum(Collection<PartRelationships> partRelationshipsByPartNum) {
        this.partRelationshipsByPartNum = partRelationshipsByPartNum;
    }

    public Collection<PartRelationships> getPartRelationshipsByPartNum_0() {
        return partRelationshipsByPartNum_0;
    }

    public void setPartRelationshipsByPartNum_0(Collection<PartRelationships> partRelationshipsByPartNum_0) {
        this.partRelationshipsByPartNum_0 = partRelationshipsByPartNum_0;
    }

    public PartCategories getPartCategoriesByPartCatId() {
        return partCategoriesByPartCatId;
    }

    public void setPartCategoriesByPartCatId(PartCategories partCategoriesByPartCatId) {
        this.partCategoriesByPartCatId = partCategoriesByPartCatId;
    }
}
