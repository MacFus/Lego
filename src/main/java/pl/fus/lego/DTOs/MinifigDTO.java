package pl.fus.lego.DTOs;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.Objects;

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

    public MinifigDTO(String figNum, String name, Integer numParts, String imgUrl, String theme, String parentTheme) {
        this.figNum = figNum;
        this.name = name;
        this.numParts = numParts;
        this.imgUrl = imgUrl;
        this.theme = theme;
        this.parentTheme = parentTheme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinifigDTO that = (MinifigDTO) o;
        return Objects.equals(figNum, that.figNum) && Objects.equals(theme, that.theme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(figNum, theme);
    }
}
