package pl.fus.lego.UTILS;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pl.fus.lego.DTOs.MinifigDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {
    public static PageRequest getPageRequest(Criteria cr, int offset, int pageSize) {
        PageRequest pr = null;
        if (cr.getSortField() != null) {
            if (cr.getDirection().equals("DESC")) {
                pr = PageRequest.of(offset, pageSize).withSort(Sort.by(cr.getSortField()).descending());
            } else {
                pr = PageRequest.of(offset, pageSize).withSort(Sort.by(cr.getSortField()).ascending());
            }
        }
        return pr;
    }

    public static void getMinifigsNoDuplicates(List<MinifigDTO> list){
        Set<MinifigDTO> uniqueElements = new HashSet<>(list);
        list.clear();
        list.addAll(uniqueElements);
//        return uniqueElements;
    }
}
