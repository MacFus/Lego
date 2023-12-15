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

    public int getQuantity() {
        return quantity;
    }
}