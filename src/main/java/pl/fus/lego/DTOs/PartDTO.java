package pl.fus.lego.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartDTO {
    private String setName;

    private String setNum;
    @JsonProperty("part_img_url")
    private String imgUrl;

    private Integer quantity;
    @JsonProperty("part_num")
    private String partNum;

    private Integer colorId;
    private String colorName;

    private String isSpare;
    @JsonProperty("name")
    private String partName;

    public PartDTO(String setName, String setNum, String imgUrl, String partNum, Integer quantity, Integer colorId, String isSpare) {
        this.setName = setName;
        this.setNum = setNum;
        this.imgUrl = imgUrl;
        this.quantity = quantity;
        this.partNum = partNum;
        this.colorId = colorId;
        this.isSpare = isSpare;
    }
    public PartDTO(String setName, String setNum, String imgUrl, String partNum, Integer quantity, Integer colorId, String colorName, String isSpare) {
        this.setName = setName;
        this.setNum = setNum;
        this.imgUrl = imgUrl;
        this.quantity = quantity;
        this.partNum = partNum;
        this.colorId = colorId;
        this.colorName = colorName;
        this.isSpare = isSpare;
    }
}
