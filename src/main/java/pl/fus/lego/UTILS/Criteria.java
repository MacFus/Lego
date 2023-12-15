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
    private String sortField;
    private String direction;
    private Integer userId;
    private String search;

    public Criteria(Integer startYear, Integer endYear, Integer minParts, Integer maxParts, String theme, String sortField, String direction) {
        this.startYear = startYear;
        this.endYear = endYear;
        this.minParts = minParts;
        this.maxParts = maxParts;
        this.theme = theme;
        this.sortField = sortField;
        this.direction = direction;
    }
    public Criteria(Integer startYear, Integer endYear, Integer minParts, Integer maxParts, String theme, String sortField, String direction, String search) {
        this.startYear = startYear;
        this.endYear = endYear;
        this.minParts = minParts;
        this.maxParts = maxParts;
        this.theme = theme;
        this.sortField = sortField;
        this.direction = direction;
        this.search = '%'+search+'%';
    }
}
