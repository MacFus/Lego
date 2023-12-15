package pl.fus.lego.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
@Data
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

}
