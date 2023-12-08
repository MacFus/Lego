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

public interface MinifigRepo extends JpaRepository<Minifigs, String>, PagingAndSortingRepository<Minifigs, String> {

    //    Query, w których określone jest Criteria.theme
    @Query("SELECT m FROM Minifigs m " +
            "JOIN InventoryMinifigs im ON m.figNum = im.figNum " +
            "JOIN Inventories i ON im.inventoryId = i.id " +
            "JOIN Sets s ON i.setNum = s.setNum " +
            "JOIN Themes t ON s.themeId = t.id " +
            "WHERE ((t.name = :#{#criteria.theme} OR COALESCE(:#{#criteria.theme}, '') = '') AND " +
            "(s.year BETWEEN :#{#criteria.startYear} AND :#{#criteria.endYear}) AND " +
            "(m.numParts BETWEEN :#{#criteria.minParts} AND :#{#criteria.maxParts}))")
    Page<Minifigs> findMinifigsByCriteria(@Param("criteria") Criteria criteria, Pageable pageable);

    //    Query, w których theme = null;
    @Query("SELECT DISTINCT m FROM Minifigs m " +
            "JOIN InventoryMinifigs im ON m.figNum = im.figNum " +
            "JOIN Inventories i ON im.inventoryId = i.id " +
            "JOIN Sets s ON i.setNum = s.setNum " +
            "WHERE ((s.year BETWEEN :#{#criteria.startYear} AND :#{#criteria.endYear}) " +
            "AND (m.numParts BETWEEN :#{#criteria.minParts} AND :#{#criteria.maxParts}))")
    Page<Minifigs> findMinifigsNoTheme(@Param("criteria") Criteria criteria, PageRequest pr);
}
