package pl.fus.lego.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartSubstituteDTO {
    private String relType;
    private String childPartNum;
    private String parentPartNum;
}
