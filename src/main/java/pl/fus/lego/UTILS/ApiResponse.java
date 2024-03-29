package pl.fus.lego.UTILS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
    List<Result> results = null;
    List<T> response = null;
    int partsCount;
    Boolean isEmbedded = false;
    int pages;

    public List<Result> getResults() {
        return results;
    }

    public ApiResponse(int count, List<Result> results) {
        this.count = count;
        this.results = results;
    }

    public ApiResponse(List<T> response, int count) {
        this.count = count;
        this.response = response;
    }

    public ApiResponse(List<T> response, int count, int partsCount) {
        this.count = count;
        this.response = response;
        this.partsCount = partsCount;
    }
    public ApiResponse(List<T> response, int count, int partsCount, int pages) {
        this.count = count;
        this.response = response;
        this.partsCount = partsCount;
        this.pages = pages;
    }
    public ApiResponse(List<T> response, int count, int partsCount, Boolean isEmbedded) {
        this.count = count;
        this.response = response;
        this.partsCount = partsCount;
        this.isEmbedded = isEmbedded;
    }
}
