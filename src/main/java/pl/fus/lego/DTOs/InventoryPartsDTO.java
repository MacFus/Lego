package pl.fus.lego.DTOs;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryPartsDTO {
    private String setName;
    private String setNum;
    private String setImgUrl;
    private String setTheme;
    private String setParentTheme;
    private String partNum;
    private String colorId;
    private Integer quantityStr;
    private Integer setQuantity;
    private String imgUrl;
    private String isSpare;

    public InventoryPartsDTO(String setName, String setNum,String setImgUrl, String setTheme, String setParentTheme, String partNum, String colorId, Integer quantityStr, String imgUrl, Integer setQuantity) {
        this.setName = setName;
        this.setNum = setNum;
        this.setImgUrl = setImgUrl;
        this.setTheme = setTheme;
        this.setParentTheme = setParentTheme;
        this.partNum = partNum;
        this.colorId = colorId;
        this.quantityStr = quantityStr;
        this.imgUrl = imgUrl;
        this.setQuantity = setQuantity;
    }

    public InventoryPartsDTO(String setName, String setNum,String setImgUrl, String partNum, String colorId, Integer quantityStr, String imgUrl,  String isSpare) {
        this.setName = setName;
        this.setNum = setNum;
        this.setImgUrl = setImgUrl;
        this.partNum = partNum;
        this.colorId = colorId;
        this.quantityStr = quantityStr;
        this.imgUrl = imgUrl;
        this.isSpare = isSpare;
    }
    public InventoryPartsDTO(String setName, String setNum,String setImgUrl, String partNum, String colorId, Integer quantityStr, String imgUrl, Integer setQuantity, String isSpare) {
        this.setName = setName;
        this.setNum = setNum;
        this.setImgUrl = setImgUrl;
        this.partNum = partNum;
        this.colorId = colorId;
        this.quantityStr = quantityStr;
        this.imgUrl = imgUrl;
        this.setQuantity = setQuantity;
        this.isSpare = isSpare;
    }

    public InventoryPartsDTO(String partNum, String colorId) {
        this.partNum = partNum;
        this.colorId = colorId;
    }
}
