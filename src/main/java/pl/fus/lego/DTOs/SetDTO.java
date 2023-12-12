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
    private int numParts;
    private String imgUrl;

    public SetDTO(String setNum, String name, int year, int themeId, int numParts, String imgUrl) {
        this.setNum = setNum;
        this.name = name;
        this.year = year;
        this.themeId = themeId;
        this.numParts = numParts;
        this.imgUrl = imgUrl;
    }
}
