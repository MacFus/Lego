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
}
