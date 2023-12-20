package pl.fus.lego.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import pl.fus.lego.Entity.Sets;
import pl.fus.lego.UTILS.Criteria;

import java.util.List;

public interface SetRepo extends JpaRepository<Sets, String>, PagingAndSortingRepository<Sets, String> {

    //      Query, w których określone jest Criteria.theme
    @Query("SELECT s FROM Sets s  " +
            "JOIN s.theme t  " +
            "LEFT JOIN s.parentTheme pt  " +
            "WHERE (:#{#cr.theme} IS NULL OR t.name LIKE CONCAT('%', :#{#cr.theme}, '%') OR pt.name LIKE CONCAT('%', :#{#cr.theme}, '%'))  " +
            "AND (s.year BETWEEN COALESCE(:#{#cr.startYear}, 1945) AND COALESCE(:#{#cr.endYear}, 2030))  " +
            "AND (s.numParts BETWEEN COALESCE(:#{#cr.minParts}, 0) AND COALESCE(:#{#cr.maxParts}, 9999))  " +
            "AND (:#{#cr.search} IS NULL OR t.name LIKE  :#{#cr.search}   " +
            "     OR s.setNum LIKE  :#{#cr.search}   " +
            "     OR s.name LIKE  :#{#cr.search})")
    Page<Sets> findSetsByCriteria(@Param("cr") Criteria cr, Pageable pr);

    @Query("SELECT  COUNT(s.setNum) FROM Sets s " +
            "JOIN Inventories iv ON iv.setNum = s.setNum")
    List<Object> findSetsInventory();


}
