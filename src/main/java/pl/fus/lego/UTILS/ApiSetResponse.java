package pl.fus.lego.UTILS;

import lombok.Data;
import pl.fus.lego.DTOs.PartDTO;
import pl.fus.lego.DTOs.PartSubstituteDTO;
import pl.fus.lego.DTOs.SetDTO;

import java.util.List;
import java.util.Map;

@Data
public class ApiSetResponse {
    private List<SetDTO> sets;
    private Map<String, List<PartDTO>> setPartMap;
    private List<PartSubstituteDTO> substitutes;

    public ApiSetResponse(List<SetDTO> sets, Map<String, List<PartDTO>> setPartMap) {
        this.sets = sets;
        this.setPartMap = setPartMap;
    }

    public ApiSetResponse(List<SetDTO> sets, Map<String, List<PartDTO>> setPartMap, List<PartSubstituteDTO> substitutes) {
        this.sets = sets;
        this.setPartMap = setPartMap;
        this.substitutes = substitutes;
    }
}
