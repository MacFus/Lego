package pl.fus.lego.DTOs;

import lombok.Data;

@Data
public class ThemeDTO {
    private String name;

    public ThemeDTO(String name) {
        this.name = name;
    }
}
