package pl.fus.lego.UTILS;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CrudSetRequest {
    private Integer userId;
    private List<String> setNum;
    private Boolean all = false;
}
