package pl.fus.lego.UTILS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse <T>{

    int recordCount;
    List<T> response;
}
