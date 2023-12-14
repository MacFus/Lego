package pl.fus.lego.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class InventorySetsDTO {
    private String inventoryId;
    private String setNum;
    private Integer quantity;
    private String setName;
    private Integer numParts;

    public InventorySetsDTO(String inventoryId, String setNum, Integer quantity, String setName, Integer numParts) {
        this.inventoryId = inventoryId;
        this.setNum = setNum;
        this.quantity = quantity;
        this.setName = setName;
        this.numParts = numParts;
    }
}
