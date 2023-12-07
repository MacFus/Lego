package pl.fus.lego.Entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Inventories {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, length = 20)
    private String id;
    @Basic
    @Column(name = "version", nullable = false, length = 20)
    private String version;
    @Basic
    @Column(name = "set_num", nullable = false, length = 20)
    private String setNum;
    @OneToMany(mappedBy = "inventoriesByInventoryId")
    private Collection<InventoryMinifigs> inventoryMinifigsById;
    @OneToMany(mappedBy = "inventoriesByInventoryId")
    private Collection<InventoryParts> inventoryPartsById;
    @OneToMany(mappedBy = "inventoriesByInventoryId")
    private Collection<InventorySets> inventorySetsById;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSetNum() {
        return setNum;
    }

    public void setSetNum(String setNum) {
        this.setNum = setNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inventories that = (Inventories) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (setNum != null ? !setNum.equals(that.setNum) : that.setNum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (setNum != null ? setNum.hashCode() : 0);
        return result;
    }

    public Collection<InventoryMinifigs> getInventoryMinifigsById() {
        return inventoryMinifigsById;
    }

    public void setInventoryMinifigsById(Collection<InventoryMinifigs> inventoryMinifigsById) {
        this.inventoryMinifigsById = inventoryMinifigsById;
    }

    public Collection<InventoryParts> getInventoryPartsById() {
        return inventoryPartsById;
    }

    public void setInventoryPartsById(Collection<InventoryParts> inventoryPartsById) {
        this.inventoryPartsById = inventoryPartsById;
    }

    public Collection<InventorySets> getInventorySetsById() {
        return inventorySetsById;
    }

    public void setInventorySetsById(Collection<InventorySets> inventorySetsById) {
        this.inventorySetsById = inventorySetsById;
    }
}
