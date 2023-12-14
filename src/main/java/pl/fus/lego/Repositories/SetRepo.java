package pl.fus.lego.Repositories;

import org.springframework.data.domain.Page;
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

    //      Query, w których określone jest Criteria.theme
    @Query("SELECT s FROM Sets s " +
            "JOIN Themes t ON s.themeId = t.id " +
            "JOIN Themes pt ON s.themeId = pt.parentId " +
            "WHERE (t.name = :#{#cr.theme} OR pt.name = :#{#cr.theme}) AND " +
            "(s.year BETWEEN :#{#cr.startYear} AND :#{#cr.endYear}) AND " +
            "(s.numParts BETWEEN :#{#cr.minParts} AND :#{#cr.maxParts})")
    Page<Sets> findSetsByCriteria(@Param("cr") Criteria cr, Pageable pr);

    //      Query, w których theme = null;
    @Query("SELECT s FROM Sets s " +
            "WHERE ((s.year BETWEEN :#{#cr.startYear} AND :#{#cr.endYear}) AND " +
            "(s.numParts BETWEEN :#{#cr.minParts} AND :#{#cr.maxParts}))")
    Page<Sets> findSetsNoTheme(@Param("cr") Criteria cr, Pageable pr);

//    @Query("SELECT ip FROM InventoryParts ip " +
//            "JOIN Inventories i ON ip.inventoryId = i.id " +
//            "JOIN Sets s ON s.setNum = i.setNum " +
//            "WHERE s.setNum IN :list ")
//    List<InventoryParts> findPartsToSetList(@Param("list") List<String> list);


//    @Query("SELECT s, ip FROM InventoryParts ip " +
//            "JOIN Inventories i ON ip.inventoryId = i.id " +
//            "JOIN Sets s ON s.setNum = i.setNum " +
//            "JOIN Parts pts ON pts.partNum = ip.partNum " +
//            "JOIN UserSets us ON us.setNum = i.setNum " +
//            "WHERE us.userId = :#{#cr.userId}")
//    List<List<Object>> findMyPartsToSet(@Param("cr") Criteria cr);
//
//    @Query("SELECT s.setNum, ip FROM InventoryParts ip " +
//            "JOIN Inventories i ON ip.inventoryId = i.id " +
//            "JOIN Sets s ON s.setNum = i.setNum " +
//            "JOIN Parts pts ON pts.partNum = ip.partNum")
//    List<List<Object>> getAllSetsWithParts(Pageable pageable);
//
//    @Query("SELECT s.setNum, ip FROM InventoryParts ip " +
//            "JOIN Inventories i ON ip.inventoryId = i.id " +
//            "JOIN Sets s ON s.setNum = i.setNum " +
//            "JOIN Parts pts ON pts.partNum = ip.partNum " +
//            "WHERE s.setNum IN :#{#list}")
//    List<List<Object>> findPartsToSetList(@Param("list") List<String> list);

}
