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
    private int startYear = 1945;
    private int endYear = 2030;
    private int minParts = 0;
    private int maxParts = 9999;
    private String theme;
    private String sortField = "name";


}
