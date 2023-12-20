package pl.fus.lego.Repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.fus.lego.DTOs.*;
import pl.fus.lego.UTILS.Criteria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class EntityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<SetDTO> findSetsByCriteria(Criteria cr, Pageable pr){

        String sql = "SELECT s.set_num, s.name, s.year, t.name as theme_name, pt.name as p_theme_name, s.num_parts, s.img_url FROM sets s  " +
                "JOIN themes t ON t.id = s.theme_id " +
                "LEFT JOIN themes pt ON pt.id = s.p_theme_id " +
                "WHERE ((:theme IS NULL OR t.name LIKE :theme OR pt.name LIKE :theme) " +
                "AND (s.year BETWEEN COALESCE( :sYear, 1945) AND COALESCE ( :eYear, 2030)) " +
                "AND (s.num_parts BETWEEN COALESCE( :min, 0) AND COALESCE ( :max =null, 9999)) " +
                "AND (:search IS NULL OR s.name LIKE :search OR s.set_num LIKE :search)) ";
        if(cr.getSortField()!=null){
            sql += " ORDER BY " + cr.getSortField() + " " + cr.getDirection();
        }
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("theme", cr.getTheme());
        query.setParameter("sYear", cr.getStartYear());
        query.setParameter("eYear", cr.getEndYear());
        query.setParameter("min", cr.getMinParts());
        query.setParameter("max", cr.getMaxParts());
        query.setParameter("search", cr.getSearch());

        int pageNumber = pr.getPageNumber();
        int pageSize = pr.getPageSize();
        List<Object[]> results = query.setFirstResult(pageNumber*pageSize).setMaxResults(pageSize).getResultList();
        List<SetDTO> sets = new ArrayList<>();
        for (Object[] result : results) {
            sets.add(new SetDTO((String) result[0], (String) result[1], (Integer) result[2], (String) result[3], (String) result[4], (Integer) result[5], (String) result[6]));
        }
        return sets;

    }

    public List<MinifigDTO> findMinifigsByCriteria(Criteria cr, Pageable pr) {
        if(cr.getSortField().equals("year")){
            String temp = cr.getSortField();
            cr.setSortField("s." + temp);
        }else{
            String temp = cr.getSortField();
            cr.setSortField("m." + temp);
        }
        String sql = "SELECT  DISTINCT (m.fig_num), m.name, m.num_parts, m.img_url, s.set_num, t.name as theme, pt.name, s.year as p_theme FROM sets s " +
                "LEFT JOIN themes t ON s.theme_id = t.id " +
                "LEFT JOIN themes pt ON s.p_theme_id = pt.id " +
                "JOIN inventories iv ON iv.set_num = s.set_num " +
                "JOIN inventory_minifigs im ON im.inventory_id = iv.id " +
                "JOIN minifigs m ON m.fig_num = im.fig_num " +
                "WHERE((:theme IS NULL OR t.name LIKE :theme OR pt.name LIKE :theme) " +
                "AND(s.year BETWEEN COALESCE( :sYear,1945)AND COALESCE ( :eYear,2030)) " +
                "AND(m.num_parts BETWEEN COALESCE( :min,0)AND COALESCE ( :max =null, 9999)) " +
                "AND(:search IS NULL OR m.name LIKE :search)) ";
        if(cr.getSortField()!=null){
            sql += " ORDER BY " + cr.getSortField() + " " + cr.getDirection();
        }
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("theme", cr.getTheme());
        query.setParameter("sYear", cr.getStartYear());
        query.setParameter("eYear", cr.getEndYear());
        query.setParameter("min", cr.getMinParts());
        query.setParameter("max", cr.getMaxParts());
        query.setParameter("search", cr.getSearch());

        int pageNumber = pr.getPageNumber();
        int pageSize = pr.getPageSize();
        List<Object[]> results = query.setFirstResult(pageNumber*pageSize).setMaxResults(pageSize).getResultList();
        List<MinifigDTO> minifigs = new ArrayList<>();
        for (Object[] result : results) {
            minifigs.add(new MinifigDTO((String) result[0],
                    (String) result[1],
                    (Integer) result[2],
                    (String) result[3],
                    (String) result[4],
                    (String) result[5],
                    (String) result[6]));
        }
//        if (cr.getSortField() == null) {
//            minifigs.sort(Comparator.comparing(MinifigDTO::getName));
//        } else {
//            if (cr.getSortField().equals("year")) {
//                minifigs.sort(Comparator.comparing(MinifigDTO::getName));
//            }
//        }

        return minifigs;
    }

    public List<PartDTO> findPartsToSetList(List<String> setNum) {
        String sql = "SELECT s.name, s.set_num, ip.img_url, ip.part_num, ip.quantity, ip.color_id,  ip.is_spare FROM sets s" +
                " JOIN inventories iv ON iv.set_num = s.set_num " +
                " JOIN inventory_parts ip ON ip.inventory_id = iv.id " +
                " WHERE s.set_num IN :set_num";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("set_num", setNum);

        List<Object[]> results = query.getResultList();
        List<PartDTO> parts = new ArrayList<>();
        for (Object[] result : results) {
            parts.add(new PartDTO((String) result[0],
                    (String) result[1],
                    (String) result[2],
                    (String) result[3],
                    (Integer) result[4],
                    (Integer) result[5],
                    (String) result[6]));
        }
        return parts;
//        TypedQuery<PartDTO> query = entityManager.createQuery("SELECT NEW pl.fus.lego.DTOs.PartDTO(s.name, s.setNum, ip.imgUrl, ip.partNum, ip.quantity, ip.colorId,  ip.isSpare) FROM Sets s" +
//                " JOIN Inventories iv ON iv.setNum = s.setNum " +
//                " JOIN InventoryParts ip ON ip.inventoryId = iv.id " +
//                " WHERE s.setNum IN :setNum", PartDTO.class);
//        return query.setParameter("setNum", setNum).getResultList();
    }

    public List<SetDTO> findMySets(Criteria cr){
        String sql = "SELECT s.set_num, s.name, s.year, t.name, pt.name, s.num_parts, s.img_url, us.quantity FROM user_sets us " +
                " JOIN sets s ON s.set_num = us.set_num " +
                " LEFT JOIN themes t ON t.id = s.theme_id  " +
                " LEFT JOIN themes pt ON pt.id = s.p_theme_id " +
                " WHERE us.user_id = :user_id ";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("user_id", cr.getUserId());

        List<Object[]> list = query.getResultList();
        List<SetDTO> result = new ArrayList<>();
        for (Object[] o : list) {
            result.add(new SetDTO((String) o[0], (String) o[1], (Integer) o[2], (String) o[3], (String) o[4], (Integer) o[5], (String) o[6], (Integer) o[7]));
        }
        return result;
    }

    // Query, w którym w cr.userId podaje userId (przechowywane w cookies)
//    public List<SetDTO> findMySets(Criteria cr) {
//        TypedQuery<SetDTO> query = entityManager.createQuery("SELECT NEW pl.fus.lego.DTOs.SetDTO(s.setNum, s.name, s.year, s.themeId, s.numParts, s.imgUrl) FROM UserSets us " +
//                "JOIN Sets s ON s.setNum = us.setNum " +
//                "WHERE us.userId = :userId", SetDTO.class);
//        return query.setParameter("userId", cr.getUserId()).getResultList();
//    }

    public List<SetDTO> findSet(String setNum) {
        TypedQuery<SetDTO> query = entityManager.createQuery("SELECT NEW pl.fus.lego.DTOs.SetDTO(s.setNum, s.name, s.year, s.themeId, t.name, s.numParts, s.imgUrl) FROM Sets s " +
                "JOIN Themes t ON s.themeId = t.id " +
                "WHERE s.setNum = :setNum ", SetDTO.class);
        return query.setParameter("setNum", setNum).getResultList();
    }

    public List<InventorySetsDTO> findEmbeddedSets(String setNum) {
        TypedQuery<InventorySetsDTO> query = entityManager.createQuery("SELECT NEW pl.fus.lego.DTOs.InventorySetsDTO(ivs.inventoryId, ivs.setNum, ivs.quantity, s.name, s.numParts) FROM InventorySets ivs " +
                "JOIN Inventories iv ON iv.id = ivs.inventoryId " +
                "JOIN Sets s ON s.setNum = ivs.setNum " +
                "WHERE iv.setNum = :setNum", InventorySetsDTO.class);
        return query.setParameter("setNum", setNum).getResultList();
    }
}
