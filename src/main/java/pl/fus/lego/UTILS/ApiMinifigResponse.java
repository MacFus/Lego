package pl.fus.lego.UTILS;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.fus.lego.DTOs.MinifigDTO;
import pl.fus.lego.DTOs.PartDTO;
import pl.fus.lego.DTOs.PartSubstituteDTO;
import pl.fus.lego.DTOs.SetDTO;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ApiMinifigResponse {
    private MinifigDTO minifig;
    private List<SetDTO> sets;
    private List<PartDTO> parts;
}
