package pl.fus.lego.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import pl.fus.lego.Entity.InventoryParts;
import pl.fus.lego.Entity.Sets;
import pl.fus.lego.UTILS.Criteria;

import java.util.List;

public interface SetRepo extends JpaRepository<Sets, String>, PagingAndSortingRepository<Sets, String> {

//    SELECT s.*, t.name FROM sets s
//    JOIN themes t ON s.theme_id = t.id OR s.theme_id = t.parent_id
//    WHERE t.name = @theme_name OR Coalesce(@theme_name,'') = ''
//    AND (s.year BETWEEN '1920' AND '2030' ) -- filtruj według zakresu roku wydania, jeśli podano obie wartości
//    AND (s.num_parts BETWEEN '0'  AND '9999'  ) -- filtruj według zakresu ilości części, jeśli podano obie wartości
//    ORDER BY s.set_num DESC;

    //    Query, w których określone jest Criteria.theme
    @Query("SELECT s FROM Sets s " +
            "JOIN Themes t ON s.themeId = t.id " +
            "WHERE ((t.name = :#{#criteria.theme} OR COALESCE(:#{#criteria.theme}, '') = '') AND " +
            "(s.year BETWEEN :#{#criteria.startYear} AND :#{#criteria.endYear}) AND " +
            "(s.numParts BETWEEN :#{#criteria.minParts} AND :#{#criteria.maxParts}))")
    Page<Sets> findSetsByCriteria(@Param("criteria") Criteria criteria, Pageable pageable);

    //    Query, w których theme = null;
    @Query("SELECT s FROM Sets s " +
            "WHERE ((s.year BETWEEN :#{#criteria.startYear} AND :#{#criteria.endYear}) AND " +
            "(s.numParts BETWEEN :#{#criteria.minParts} AND :#{#criteria.maxParts}))")
    Page<Sets> findSetsNoTheme(@Param("criteria") Criteria criteria, PageRequest pr);

    @Query("SELECT ip FROM InventoryParts ip " +
            "JOIN Inventories i ON ip.inventoryId = i.id " +
            "JOIN Sets s ON s.setNum = i.setNum " +
            "JOIN Parts pts ON pts.partNum = ip.partNum " +
            "JOIN UserSets us ON us.setNum = i.setNum " +
            "WHERE us.userId = :#{#criteria.userId}")
    List<InventoryParts> findMyParts(@Param("criteria") Criteria criteria);

    @Query("SELECT DISTINCT s FROM InventoryParts ip " +
            "JOIN Inventories i ON ip.inventoryId = i.id " +
            "JOIN Sets s ON s.setNum = i.setNum " +
            "JOIN Parts pts ON pts.partNum = ip.partNum " +
            "JOIN UserSets us ON us.setNum = i.setNum " +
            "WHERE us.userId = :#{#criteria.userId}")
    List<Sets> findMySets(@Param("criteria") Criteria criteria);

    @Query("SELECT s, ip FROM InventoryParts ip " +
            "JOIN Inventories i ON ip.inventoryId = i.id " +
            "JOIN Sets s ON s.setNum = i.setNum " +
            "JOIN Parts pts ON pts.partNum = ip.partNum " +
            "JOIN UserSets us ON us.setNum = i.setNum " +
            "WHERE us.userId = :#{#criteria.userId}")
    List<List<Object>> findMyPartsToSet(@Param("criteria") Criteria criteria);



}
