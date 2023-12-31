package pl.fus.lego.DTOs;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetDTO {
    private String setNum;
    private String name;
    private int year;
    private int themeId;
    private String themeName;
    private String parentThemeName;
    private int numParts;
    private String imgUrl;
    private Integer quantity;
    private Double match;
    private Integer partsQuantity;

    public SetDTO(String setNum) {
        this.setNum = setNum;
    }


    SetDTO(String setNum, String name, int year, int themeId, String themeName, String parentThemeName, int numParts, String imgUrl){
        this.setNum = setNum;
        this.name = name;
        this.year = year;
        this.themeId = themeId;
        this.themeName = themeName;
        this.parentThemeName = parentThemeName;
        this.numParts = numParts;
        this.imgUrl = imgUrl;
    }

    public SetDTO(String setNum, String name, int year, String themeName, String parentThemeName, int numParts, String imgUrl) {
        this.setNum = setNum;
        this.name = name;
        this.year = year;
        this.themeName = themeName;
        this.parentThemeName = parentThemeName;
        this.numParts = numParts;
        this.imgUrl = imgUrl;
    }
    public SetDTO(String setNum, String name, int year, String themeName, String parentThemeName, int numParts, String imgUrl, Integer quantity) {
        this.setNum = setNum;
        this.name = name;
        this.year = year;
        this.themeName = themeName;
        this.parentThemeName = parentThemeName;
        this.numParts = numParts;
        this.imgUrl = imgUrl;
        this.quantity = quantity;
    }

    public SetDTO(String setNum, String name, int year, int themeId, int numParts, String imgUrl) {
        this.setNum = setNum;
        this.name = name;
        this.year = year;
        this.themeId = themeId;
        this.numParts = numParts;
        this.imgUrl = imgUrl;
    }

    public SetDTO(String setNum, String name, int year, int themeId, String themeName, int numParts, String imgUrl) {
        this.setNum = setNum;
        this.name = name;
        this.year = year;
        this.themeId = themeId;
        this.themeName = themeName;
        this.numParts = numParts;
        this.imgUrl = imgUrl;
    }
}
