package pl.fus.lego.UTILS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.fus.lego.DTOs.InventoryPartsDTO;
import pl.fus.lego.DTOs.SetDTO;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
    @JsonProperty("count")
    int count;
    @JsonProperty("results")
    List<T> response;
    int partsCount;
    public ApiResponse(int recordCount, List<T> response) {
        this.count = recordCount;
        this.response = response;
    }

    public ApiResponse(int count, List<T> response, int partsCount) {
        this.count = count;
        this.response = response;
        this.partsCount = partsCount;
    }
}
