package pl.fus.lego.Entity;

import jakarta.persistence.*;

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

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getFigNum() {
        return figNum;
    }

    public void setFigNum(String figNum) {
        this.figNum = figNum;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InventoryMinifigs that = (InventoryMinifigs) o;

        if (inventoryId != null ? !inventoryId.equals(that.inventoryId) : that.inventoryId != null) return false;
        if (figNum != null ? !figNum.equals(that.figNum) : that.figNum != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = inventoryId != null ? inventoryId.hashCode() : 0;
        result = 31 * result + (figNum != null ? figNum.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }

    public Inventories getInventoriesByInventoryId() {
        return inventoriesByInventoryId;
    }

    public void setInventoriesByInventoryId(Inventories inventoriesByInventoryId) {
        this.inventoriesByInventoryId = inventoriesByInventoryId;
    }
}
