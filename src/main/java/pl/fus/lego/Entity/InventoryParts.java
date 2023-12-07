package pl.fus.lego.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory_parts", schema = "lego", catalog = "")
public class InventoryParts {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "my_row_id", nullable = false)
    private Long myRowId;
    @Basic
    @Column(name = "inventory_id", nullable = false, length = 20)
    private String inventoryId;
    @Basic
    @Column(name = "part_num", nullable = false, length = 20)
    private String partNum;
    @Basic
    @Column(name = "color_id", nullable = false, length = 20)
    private String colorId;
    @Basic
    @Column(name = "quantity", nullable = false, length = 20)
    private String quantity;
    @Basic
    @Column(name = "is_spare", nullable = false, length = 20)
    private String isSpare;
    @Basic
    @Column(name = "img_url", nullable = true, length = 255)
    private String imgUrl;
    @ManyToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private Inventories inventoriesByInventoryId;

    public Object getMyRowId() {
        return myRowId;
    }

    public void setMyRowId(Long myRowId) {
        this.myRowId = myRowId;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getPartNum() {
        return partNum;
    }

    public void setPartNum(String partNum) {
        this.partNum = partNum;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIsSpare() {
        return isSpare;
    }

    public void setIsSpare(String isSpare) {
        this.isSpare = isSpare;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InventoryParts that = (InventoryParts) o;

        if (myRowId != null ? !myRowId.equals(that.myRowId) : that.myRowId != null) return false;
        if (inventoryId != null ? !inventoryId.equals(that.inventoryId) : that.inventoryId != null) return false;
        if (partNum != null ? !partNum.equals(that.partNum) : that.partNum != null) return false;
        if (colorId != null ? !colorId.equals(that.colorId) : that.colorId != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (isSpare != null ? !isSpare.equals(that.isSpare) : that.isSpare != null) return false;
        if (imgUrl != null ? !imgUrl.equals(that.imgUrl) : that.imgUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = myRowId != null ? myRowId.hashCode() : 0;
        result = 31 * result + (inventoryId != null ? inventoryId.hashCode() : 0);
        result = 31 * result + (partNum != null ? partNum.hashCode() : 0);
        result = 31 * result + (colorId != null ? colorId.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (isSpare != null ? isSpare.hashCode() : 0);
        result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
        return result;
    }

    public Inventories getInventoriesByInventoryId() {
        return inventoriesByInventoryId;
    }

    public void setInventoriesByInventoryId(Inventories inventoriesByInventoryId) {
        this.inventoriesByInventoryId = inventoriesByInventoryId;
    }
}
