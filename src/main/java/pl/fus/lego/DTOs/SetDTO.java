package pl.fus.lego.DTOs;

import lombok.Data;

@Data
public class SetDTO {
    private String setNum;
    private String name;
    private int year;
    private int themeId;
    private int numParts;
    private String imgUrl;


}
