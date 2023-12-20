package pl.fus.lego.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import pl.fus.lego.Entity.Minifigs;
import pl.fus.lego.UTILS.Criteria;

import java.util.List;

public interface MinifigRepo extends JpaRepository<Minifigs, String>, PagingAndSortingRepository<Minifigs, String> {

    //    Query, w których określone jest Criteria.theme
    @Query("SELECT DISTINCT m.figNum, m.name, m.numParts, m.imgUrl, im.figNum FROM Minifigs m " +
            "JOIN InventoryMinifigs im ORDER BY m.name LIMIT 1000")
//            "JOIN Inventories iv ON iv.id = im.inventoryId " +
//            "LEFT JOIN Sets s ON s.setNum = iv.setNum " +
//            "LEFT JOIN Themes t ON s.themeId = t.id " +
//            "LEFT JOIN Themes pt ON s.pThemeId = t.id " +
//            "WHERE (:#{#cr.theme} IS NULL OR t.name LIKE CONCAT('%', :#{#cr.theme}, '%') OR pt.name LIKE CONCAT('%', :#{#cr.theme}, '%'))  " +
//            "AND (s.year BETWEEN COALESCE(:#{#cr.startYear}, 1945) AND COALESCE(:#{#cr.endYear}, 2030))  " +
//            "AND (m.numParts BETWEEN COALESCE(:#{#cr.minParts}, 0) AND COALESCE(:#{#cr.maxParts}, 9999))  " )
//            "AND (:#{#cr.search} IS NULL OR t.name LIKE  :#{#cr.search}   " +
//            "     OR s.setNum LIKE  :#{#cr.search}   " +
//            "     OR s.name LIKE  :#{#cr.search})")
    List<Object> findMinifigsByCriteria(@Param("cr") Criteria cr, Pageable pr);

    //    Query, w których theme = null;
    @Query("SELECT DISTINCT m FROM Minifigs m " +
            "JOIN InventoryMinifigs im ON m.figNum = im.figNum " +
            "JOIN Inventories i ON im.inventoryId = i.id " +
            "JOIN Sets s ON i.setNum = s.setNum " +
            "WHERE ((s.year BETWEEN :#{#criteria.startYear} AND :#{#criteria.endYear}) " +
            "AND (m.numParts BETWEEN :#{#criteria.minParts} AND :#{#criteria.maxParts}))")
    Page<Minifigs> findMinifigsNoTheme(@Param("criteria") Criteria criteria, PageRequest pr);
}
