package pl.fus.lego.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "inventory_sets", schema = "lego", catalog = "")
@IdClass(InventorySetsPK.class)
public class InventorySets {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "inventory_id", nullable = false, length = 20, insertable=false, updatable=false)
    private String inventoryId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "set_num", nullable = false, length = 20)
    private String setNum;
    @Basic
    @Column(name = "quantity", nullable = false, length = 20)
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    @JsonIgnore
    private Inventories inventoriesByInventoryId;

}
