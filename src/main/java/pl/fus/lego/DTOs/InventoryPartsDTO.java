package pl.fus.lego.DTOs;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryPartsDTO {
    private String setName;
    private String setNum;
    private String setImgUrl;
    private String setTheme;
    private String setParentTheme;
    private String partNum;
    private String colorId;
    private String quantityStr;
    private Integer quantity;
    private String imgUrl;

    public InventoryPartsDTO(String setName, String setNum,String setImgUrl, String setTheme, String setParentTheme, String partNum, String colorId, String quantityStr, String imgUrl) {
        this.setName = setName;
        this.setNum = setNum;
        this.setImgUrl = setImgUrl;
        this.setTheme = setTheme;
        this.setParentTheme = setParentTheme;
        this.partNum = partNum;
        this.colorId = colorId;
        this.quantityStr = quantityStr;
        this.imgUrl = imgUrl;
    }

    public InventoryPartsDTO(String partNum, String colorId) {
        this.partNum = partNum;
        this.colorId = colorId;
    }
}
