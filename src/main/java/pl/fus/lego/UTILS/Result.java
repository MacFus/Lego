package pl.fus.lego.UTILS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pl.fus.lego.DTOs.ColorDTO;
import pl.fus.lego.DTOs.PartDTO;

@Data
public class Result {
    @JsonProperty("id")
    private long id;
    @JsonProperty("inv_part_id")
    private long invPartId;
    @JsonProperty("part")
    private PartDTO part;
    @JsonProperty("color")
    private ColorDTO color;
    @JsonProperty("set_num")
    private String setNum;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("is_spare")
    private boolean isSpare;
    @JsonProperty("element_id")
    private String elementId;
    @JsonProperty("num_sets")
    private int numSets;

    public Result(long id, long invPartId, PartDTO part, ColorDTO color, String setNum, int quantity, boolean isSpare, String elementId, int numSets) {
        this.id = id;
        this.invPartId = invPartId;
        this.part = part;
        this.color = color;
        this.setNum = setNum;
        this.part.setQuantity(quantity);
        this.part.setColorId(color.getId());
        this.part.setIsSpare(String.valueOf(isSpare));
        this.part.setSetNum(setNum);
        this.quantity = quantity;
        this.isSpare = isSpare;
        this.elementId = elementId;
        this.numSets = numSets;
    }

}