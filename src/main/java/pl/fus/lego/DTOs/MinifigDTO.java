package pl.fus.lego.DTOs;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class MinifigDTO {
    private String figNum;
    private String name;
    private Integer numParts;
    private String imgUrl;
    private String setNum;
    private String theme;
    private String parentTheme;

    public MinifigDTO(String figNum, String name, Integer numParts, String imgUrl, String setNum, String theme, String parentTheme) {
        this.figNum = figNum;
        this.name = name;
        this.numParts = numParts;
        this.imgUrl = imgUrl;
        this.setNum = setNum;
        this.theme = theme;
        this.parentTheme = parentTheme;
    }
}
