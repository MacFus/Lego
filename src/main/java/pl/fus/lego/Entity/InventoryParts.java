package pl.fus.lego.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "inventory_parts", schema = "lego", catalog = "")
public class InventoryParts {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "my_row_id", nullable = false)
    @JsonIgnore
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
    private Integer quantity;
    @Basic
    @Column(name = "is_spare", nullable = false, length = 20)
    private String isSpare;
    @Basic
    @Column(name = "img_url", nullable = true, length = 255)
    private String imgUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    @JsonIgnore
    private Inventories inventoriesByInventoryId;

}
