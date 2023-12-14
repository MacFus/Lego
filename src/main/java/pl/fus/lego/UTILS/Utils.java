package pl.fus.lego.UTILS;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
}
