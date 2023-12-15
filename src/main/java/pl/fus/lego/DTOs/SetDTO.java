package pl.fus.lego.DTOs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SetDTO {
    private String setNum;
    private String name;
    private int year;
    private int themeId;
    private String themeName;
    private String parentThemeName;
    private int numParts;
    private String imgUrl;

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
