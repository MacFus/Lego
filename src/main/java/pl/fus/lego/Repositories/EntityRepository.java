package pl.fus.lego.Repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.fus.lego.DTOs.InventoryPartsDTO;
import pl.fus.lego.DTOs.InventorySetsDTO;
import pl.fus.lego.DTOs.PartDTO;
import pl.fus.lego.DTOs.SetDTO;
import pl.fus.lego.UTILS.Criteria;

import java.util.Comparator;
import java.util.List;

@Repository
public class EntityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // add sorting ASC DESC BY FIELD
    public List<SetDTO> findSetsByCriteria(Criteria cr, Pageable pr) {
        String query = " SELECT distinct NEW pl.fus.lego.DTOs.SetDTO(s.setNum, s.name, s.year, s.themeId, s.numParts, s.imgUrl) FROM Sets s ";
        query += " JOIN Themes t ON t.id = s.themeId JOIN Themes pt ON pt.parentId = s.themeId WHERE ";
        query += " (s.year >= :startYear AND s.year <= :endYear) AND (s.numParts >= :minParts AND s.numParts <= :maxParts) AND s.numParts != 0";
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
        List<SetDTO> resultList = q.setFirstResult(pr.getPageNumber() * pr.getPageSize())
                .setMaxResults(pr.getPageSize())
                .getResultList();
        resultList.sort(Comparator.comparing(SetDTO::getName));
        return resultList;
    }

    public List<SetDTO> findAllSets(Criteria cr, Pageable pr) {
        String query = " SELECT distinct NEW pl.fus.lego.DTOs.SetDTO(s.setNum, s.name, s.year, s.themeId, s.numParts, s.imgUrl) FROM Sets s WHERE s.numParts != 0";
        TypedQuery<SetDTO> q = entityManager.createQuery(query, SetDTO.class);
        List<SetDTO> resultList = q.setFirstResult(pr.getPageNumber() * pr.getPageSize())
                .setMaxResults(pr.getPageSize())
                .getResultList();
        resultList.sort(Comparator.comparing(SetDTO::getName));
        return resultList;
    }

    public List<InventoryPartsDTO> findPartsToSetList(List<String> setNum, Integer userId) {
        TypedQuery<InventoryPartsDTO> query = entityManager.createQuery("SELECT NEW pl.fus.lego.DTOs.InventoryPartsDTO(s.name, s.setNum, s.imgUrl, ip.partNum, ip.colorId, ip.quantity, ip.imgUrl, us.quantity, ip.isSpare) FROM Sets s" +
                " JOIN Inventories iv ON iv.setNum = s.setNum " +
                " JOIN InventoryParts ip ON ip.inventoryId = iv.id " +
                " JOIN UserSets us ON us.setNum = s.setNum" +
                " WHERE s.setNum IN :setNum AND us.userId = :userId", InventoryPartsDTO.class);
        return query.setParameter("setNum", setNum).setParameter("userId", userId).getResultList();
    }

    public List<PartDTO> findPartsToSetList(List<String> setNum) {
        TypedQuery<PartDTO> query = entityManager.createQuery("SELECT NEW pl.fus.lego.DTOs.PartDTO(s.name, s.setNum, ip.imgUrl, ip.partNum, ip.quantity, ip.colorId,  ip.isSpare) FROM Sets s" +
                " JOIN Inventories iv ON iv.setNum = s.setNum " +
                " JOIN InventoryParts ip ON ip.inventoryId = iv.id " +
                " WHERE s.setNum IN :setNum", PartDTO.class);
        return query.setParameter("setNum", setNum).getResultList();
    }

    // Query, w którym w cr.userId podaje userId (przechowywane w cookies)
    public List<SetDTO> findMySets(Criteria cr) {
        TypedQuery<SetDTO> query = entityManager.createQuery("SELECT NEW pl.fus.lego.DTOs.SetDTO(s.setNum, s.name, s.year, s.themeId, s.numParts, s.imgUrl) FROM UserSets us " +
                "JOIN Sets s ON s.setNum = us.setNum " +
                "WHERE us.userId = :userId", SetDTO.class);
        return query.setParameter("userId", cr.getUserId()).getResultList();
    }

    public List<SetDTO> findSet(String setNum) {
        TypedQuery<SetDTO> query = entityManager.createQuery("SELECT NEW pl.fus.lego.DTOs.SetDTO(s.setNum, s.name, s.year, s.themeId, t.name, s.numParts, s.imgUrl) FROM Sets s " +
                "JOIN Themes t ON s.themeId = t.id " +
                "WHERE s.setNum = :setNum ", SetDTO.class);
        return query.setParameter("setNum", setNum).getResultList();
    }

    public List<InventorySetsDTO> findEmbeddedSets(String setNum){
        TypedQuery<InventorySetsDTO> query = entityManager.createQuery("SELECT NEW pl.fus.lego.DTOs.InventorySetsDTO(ivs.inventoryId, ivs.setNum, ivs.quantity, s.name, s.numParts) FROM InventorySets ivs " +
                "JOIN Inventories iv ON iv.id = ivs.inventoryId " +
                "JOIN Sets s ON s.setNum = ivs.setNum " +
                "WHERE iv.setNum = :setNum", InventorySetsDTO.class);
        return query.setParameter("setNum", setNum).getResultList();
    }

//    public List<InventoryParts> findPartsToSetList(List<String> setNum) {
//        List<InventoryParts> result = new ArrayList<>();
//        for (String str : setNum) {
////            TypedQuery<InventoryParts> query = entityManager.createQuery("SELECT ip FROM Sets s" +
////                    " JOIN Inventories iv ON iv.setNum = s.setNum " +
////                    " JOIN InventoryParts ip ON ip.inventoryId = iv.id " +
////                    " WHERE s.setNum = :setNum", InventoryParts.class);
//            TypedQuery<InventoryParts> query = entityManager.createQuery("SELECT ip FROM Inventories iv" +
//                    " JOIN Sets s ON iv.setNum = s.setNum " +
//                    " JOIN InventoryParts ip ON ip.inventoryId = iv.id " +
//                    " WHERE s.setNum = :setNum", InventoryParts.class);
//            List<InventoryParts> entity = query.setParameter("setNum", str).getResultList();
//            result.addAll(entity);
//        }
//        return result;
//    }
//    public List<InventoryParts> getInventoryPartsForSets(List<String> setNums) {
//        String jpql = "SELECT ip FROM InventoryParts ip " +
//                "JOIN Inventories inv ON ip.inventoryId = inv.id " +
//                "JOIN Sets s ON inv.setNum = s.setNum " +
//                "WHERE s.setNum IN :setNums";
//
//        TypedQuery<InventoryParts> query = entityManager.createQuery(jpql, InventoryParts.class);
//        query.setParameter("setNums", setNums);
//
//        return query.getResultList();
//    }


}
