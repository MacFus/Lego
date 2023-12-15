package pl.fus.lego.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ColorDTO {
    private int id;
    private String name;
    private String rgb;
    @JsonProperty("is_trans")
    private boolean isTrans;

}
