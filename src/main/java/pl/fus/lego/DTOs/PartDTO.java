package pl.fus.lego.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartDTO {
    private String setName;
    private String setNum;
    private String imgUrl;
    private Integer quantity;
    private String partNum;
    private String colorId;
    private String isSpare;
    private String partName;

    public PartDTO(String setName, String setNum, String imgUrl, String partNum, Integer quantity, String colorId, String isSpare) {
        this.setName = setName;
        this.setNum = setNum;
        this.imgUrl = imgUrl;
        this.quantity = quantity;
        this.partNum = partNum;
        this.colorId = colorId;
        this.isSpare = isSpare;
    }

    public String getSetName() {
        return setName;
    }

    @JsonProperty("set_num")
    public String getSetNum() {
        return setNum;
    }

    @JsonProperty("part_img_url")
    public String getImgUrl() {
        return imgUrl;
    }

    @JsonProperty("quantity")
    public Integer getQuantity() {
        return quantity;
    }

    @JsonProperty("part_num")
    public String getPartNum() {
        return partNum;
    }
    @JsonProperty("name")
    public String getPartName() {
        return partName;
    }

    @JsonProperty("color.id")
    public String getColorId() {
        return colorId;
    }

    @JsonProperty("is_spare")
    public String getIsSpare() {
        return isSpare;
    }
}
