package pl.fus.lego.UTILS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.fus.lego.DTOs.InventoryPartsDTO;
import pl.fus.lego.DTOs.SetDTO;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse <T>{

    int recordCount;
    List<T> response;
    Map<SetDTO, List<InventoryPartsDTO>> responseMap;

    public ApiResponse(int recordCount, List<T> response) {
        this.recordCount = recordCount;
        this.response = response;
    }

    public ApiResponse(int size, Map<SetDTO, List<InventoryPartsDTO>> map) {
        recordCount = size;
        responseMap = map;
    }
}
