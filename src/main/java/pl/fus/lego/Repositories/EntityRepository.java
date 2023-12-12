package pl.fus.lego.Repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.fus.lego.DTOs.InventoryPartsDTO;
import pl.fus.lego.DTOs.SetDTO;
import pl.fus.lego.UTILS.Criteria;

import java.util.List;

@Repository
public class EntityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<SetDTO> findSetsByCriteria(Criteria cr, Pageable pr) {
        String query = " SELECT distinct NEW pl.fus.lego.DTOs.SetDTO(s.setNum, s.name, s.year, s.themeId, s.numParts, s.imgUrl) FROM Sets s ";
        query += " JOIN Themes t ON t.id = s.themeId JOIN Themes pt ON pt.parentId = s.themeId WHERE ";
        query += " (s.year >= :startYear AND s.year <= :endYear) AND (s.numParts >= :minParts AND s.numParts <= :maxParts)";
        TypedQuery<SetDTO> q;
        q = entityManager.createQuery(query, SetDTO.class)
                .setParameter("startYear", cr.getStartYear())
                .setParameter("endYear", cr.getEndYear())
                .setParameter("minParts", cr.getMinParts())
                .setParameter("maxParts", cr.getMaxParts());
        if (cr.getTheme() != null) {
            query += " AND (t.name = :theme OR pt.name = :theme) ";
            q = entityManager.createQuery(query, SetDTO.class)
                    .setParameter("startYear", cr.getStartYear())
                    .setParameter("endYear", cr.getEndYear())
                    .setParameter("minParts", cr.getMinParts())
                    .setParameter("maxParts", cr.getMaxParts())
                    .setParameter("theme", cr.getTheme());
        }
        return q.getResultList();
    }

    public List<InventoryPartsDTO> findAllWithTheme(String theme) {
        TypedQuery<InventoryPartsDTO> query = entityManager.createQuery("SELECT NEW pl.fus.lego.DTOs.InventoryPartsDTO(s.name, s.setNum, s.imgUrl, t.name, pt.name, ip.partNum, ip.colorId, ip.quantity, ip.imgUrl) FROM Sets s" +
                " JOIN Inventories iv ON iv.setNum = s.setNum " +
                " JOIN InventoryParts ip ON ip.inventoryId = iv.id " +
                " JOIN Themes t ON s.themeId = t.id " +
                " JOIN Themes pt ON s.themeId = pt.parentId " +
                " WHERE t.name = ?1 OR pt.name = ?1", InventoryPartsDTO.class);
        return query.setParameter(1, theme).getResultList();
    }


}
