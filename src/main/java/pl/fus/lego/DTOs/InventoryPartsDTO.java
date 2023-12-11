package pl.fus.lego.DTOs;


import lombok.Data;

@Data
public class InventoryPartsDTO {
    private String partNum;
    private String colorId;
    private String quantity;
    private String imgUrl;
}
