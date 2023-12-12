package pl.fus.lego.UTILS;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Criteria {
    private Integer startYear = 1945;
    private Integer endYear = 2030;
    private Integer minParts = 0;
    private Integer maxParts = 9999;
    private String theme;
    private String sortField = "name";
    private Integer userId;


}
