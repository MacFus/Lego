package pl.fus.lego.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "inventory_minifigs", schema = "lego", catalog = "")
@IdClass(InventoryMinifigsPK.class)
public class InventoryMinifigs {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "inventory_id", nullable = false, length = 20)
    private String inventoryId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "fig_num", nullable = false, length = 20)
    private String figNum;
    @Basic
    @Column(name = "quantity", nullable = false, length = 20)
    private String quantity;
    @ManyToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id", nullable = false)
    private Inventories inventoriesByInventoryId;

}
