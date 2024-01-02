package pl.fus.lego.Repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.fus.lego.DTOs.*;
import pl.fus.lego.Entity.Role;
import pl.fus.lego.Entity.User;
import pl.fus.lego.Entity.UserSets;
import pl.fus.lego.UTILS.CrudSetRequest;
import pl.fus.lego.UTILS.ApiResponse;
import pl.fus.lego.UTILS.Criteria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class EntityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public ApiResponse<SetDTO> findSetsByCriteria(Criteria cr, Pageable pr) {
        if (cr.getSortField() != null) {
            String temp = cr.getSortField();
            cr.setSortField("s." + temp);
        }
        String sql = "SELECT DISTINCT (s.set_num), s.name, s.year, t.name, pt.name, s.num_parts, s.img_url FROM sets s " +
                "LEFT JOIN themes t ON s.theme_id = t.id " +
                "LEFT JOIN themes pt ON s.p_theme_id = pt.id " +
                "LEFT JOIN inventories iv ON s.set_num = iv.set_num " +
                "LEFT JOIN inventory_sets ivs ON s.set_num = ivs.set_num " +
                "LEFT JOIN inventory_minifigs im ON im.inventory_id = iv.id " +
                "LEFT JOIN minifigs m ON m.fig_num = im.fig_num " +
                "JOIN inventory_parts ip ON ivs.inventory_id = ip.inventory_id OR ip.inventory_id = iv.id " +
                "WHERE (:theme IS NULL OR t.name LIKE :theme OR pt.name LIKE :theme) " +
                "AND (s.year BETWEEN COALESCE( :sYear, 1945) AND COALESCE(:eYear, 2030)) " +
                "AND (s.num_parts BETWEEN COALESCE(:min, 0) AND COALESCE(:max, 9999)) " +
                "AND (:search IS NULL OR t.name LIKE :search OR s.set_num LIKE :search OR s.name LIKE :search OR m.name LIKE :search)";
        if (cr.getSortField() != null) {
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
        List<Object[]> results = query.getResultList();
        List<Object[]> subResults;
        int size = results.size();
        if (size < (pageNumber + 1) * pageSize) {
            subResults = results.subList(pageNumber * pageSize, size);
        } else {
            subResults = results.subList(pageNumber * pageSize, pageNumber * pageSize + pageSize);
        }

        List<SetDTO> sets = new ArrayList<>();
        SetDTO.SetDTOBuilder builder = SetDTO.builder();
        int pages = size / pageSize;
        if (size > pr.getPageSize())
            pages += 1;
        for (Object[] result : subResults) {
            sets.add(builder.setNum((String) result[0])
                    .name((String) result[1])
                    .year((Integer) result[2])
                    .themeName((String) result[3])
                    .parentThemeName((String) result[4])
                    .numParts((Integer) result[5])
                    .imgUrl((String) result[6]).build());
        }
        return new ApiResponse<>(sets, sets.size(), 0, pages);

    }

    public ApiResponse<MinifigDTO> findMinifigsByCriteria(Criteria cr, Pageable pr) {
        if (cr.getSortField().equals("year")) {
            String temp = cr.getSortField();
            cr.setSortField("s." + temp);
        } else {
            String temp = cr.getSortField();
            cr.setSortField("m." + temp);
        }
        String sql = "SELECT  DISTINCT (m.fig_num), m.name, m.num_parts, m.img_url, t.name as theme, pt.name FROM sets s " +
                "LEFT JOIN themes t ON s.theme_id = t.id " +
                "LEFT JOIN themes pt ON s.p_theme_id = pt.id " +
                "LEFT JOIN inventories iv ON s.set_num = iv.set_num " +
                "LEFT JOIN inventory_sets ivs ON s.set_num = ivs.set_num " +
                "LEFT JOIN inventory_minifigs im ON im.inventory_id = iv.id " +
                "LEFT JOIN minifigs m ON m.fig_num = im.fig_num " +
                "JOIN inventory_parts ip ON ivs.inventory_id = ip.inventory_id OR ip.inventory_id = iv.id " +
                "WHERE (:theme IS NULL OR t.name LIKE :theme OR pt.name LIKE :theme) " +
                "AND (s.year BETWEEN COALESCE( :sYear, 1945) AND COALESCE(:eYear, 2030)) " +
                "AND (m.num_parts BETWEEN COALESCE(:min, 0) AND COALESCE(:max, 9999)) " +
                "AND(:search IS NULL OR m.name LIKE :search) ";
        if (cr.getSortField() != null) {
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
        List<Object[]> results = query.getResultList();
        List<Object[]> subResults;
        int size = results.size();
        if (size < (pageNumber + 1) * pageSize) {
            subResults = results.subList(pageNumber * pageSize, size);
        } else {
            subResults = results.subList(pageNumber * pageSize, pageNumber * pageSize + pageSize);
        }
        int pages = size / pageSize;
        if (size > pr.getPageSize())
            pages += 1;

        List<MinifigDTO> minifigs = new ArrayList<>();
        for (Object[] result : subResults) {
            minifigs.add(new MinifigDTO((String) result[0],
                    (String) result[1],
                    (Integer) result[2],
                    (String) result[3],
                    (String) result[4],
                    (String) result[5]));
        }
        return new ApiResponse<>(minifigs, minifigs.size(), 0, pages);
    }

    public List<PartDTO> findPartsToSetList(List<String> setNum) {
        String sql = "SELECT s.name, s.set_num, ip.img_url, ip.part_num, ip.quantity, ip.color_id, c.name,  ip.is_spare FROM sets s" +
                " JOIN inventories iv ON iv.set_num = s.set_num " +
                " JOIN inventory_parts ip ON ip.inventory_id = iv.id " +
                " JOIN colors c ON ip.color_id = c.id " +
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
                    (String) result[6],
                    (String) result[7]));
        }
        return parts;
    }

    public List<SetDTO> findMySets(Integer userId) {
        String sql = "SELECT s.set_num, s.name, s.year, t.name, pt.name, s.num_parts, s.img_url, us.quantity FROM user_sets us " +
                " JOIN sets s ON s.set_num = us.set_num " +
                " LEFT JOIN themes t ON t.id = s.theme_id  " +
                " LEFT JOIN themes pt ON pt.id = s.p_theme_id " +
                " WHERE us.user_id = :user_id ";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("user_id", userId);

        List<Object[]> list = query.getResultList();
        List<SetDTO> result = new ArrayList<>();
        SetDTO.SetDTOBuilder builder = SetDTO.builder();
        for (Object[] element : list) {
            result.add(builder.setNum((String) element[0])
                    .name((String) element[1])
                    .year((Integer) element[2])
                    .themeName((String) element[3])
                    .parentThemeName((String) element[4])
                    .numParts((Integer) element[5])
                    .imgUrl((String) element[6])
                    .quantity((Integer) element[7]).build());
        }
        return result;
    }

    public List<SetDTO> findSetAndParts(String setNum) {
        String sql = "SELECT DISTINCT (s.set_num), s.name, s.year, t.name, pt.name, s.num_parts, s.img_url, ivs.quantity FROM sets s " +
                " LEFT JOIN themes t ON s.theme_id = t.id " +
                " LEFT JOIN themes pt ON s.p_theme_id = pt.id " +
                " LEFT JOIN inventories iv ON s.set_num = iv.set_num " +
                " LEFT JOIN inventory_sets ivs ON s.set_num = ivs.set_num " +
                " WHERE s.set_num = :set_num";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("set_num", setNum);
        List<Object[]> results = query.getResultList();
        if ((Integer) results.get(0)[5] == 0) {
            sql = "SELECT s.set_num, s.name, s.year, t.name, pt.name, s.num_parts, s.img_url, ivs.quantity FROM sets s " +
                    "JOIN themes t ON t.id = s.theme_id " +
                    "LEFT JOIN themes pt ON pt.id = s.p_theme_id " +
                    "JOIN inventory_sets ivs ON ivs.set_num = s.set_num " +
                    "JOIN inventories iv ON iv.id = ivs.inventory_id" +
                    "    WHERE iv.set_num = :set_num";
            query = entityManager.createNativeQuery(sql);
            query.setParameter("set_num", setNum);
            List<Object[]> embeddedSets = query.getResultList();
            for (Object[] set : embeddedSets) {
                results.add(set);
            }
        }


        List<SetDTO> result = new ArrayList<>();
        SetDTO.SetDTOBuilder builder = SetDTO.builder();
        for (Object[] set : results) {
            result.add(builder.setNum((String) set[0])
                    .name((String) set[1])
                    .year((Integer) set[2])
                    .themeName((String) set[3])
                    .parentThemeName((String) set[4])
                    .numParts((Integer) set[5])
                    .imgUrl((String) set[6])
                    .quantity((Integer) set[7]).build());
        }

        return result;
    }

    public User findUser(int userId) {
        String sql = "SELECT u.id, u.firstname, u.lastname, u.email FROM _user u" +
                " WHERE u.id = :userId";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userId", userId);
        List<Object[]> results = query.getResultList();
        User user = new User();
        for (Object[] obj : results) {
            user.setId((Integer) obj[0]);
            user.setFirstname((String) obj[1]);
            user.setLastname((String) obj[2]);
            user.setEmail((String) obj[3]);
            user.setRole(Role.USER);
        }
        return user;
    }

    public List<ThemeDTO> findThemes() {
        TypedQuery<ThemeDTO> query = entityManager.createQuery("SELECT NEW pl.fus.lego.DTOs.ThemeDTO(t.name) FROM Themes t", ThemeDTO.class);
        return query.getResultList();
    }

    public List<PartSubstituteDTO> findSubstitutes(List<String> partNums) {
        String queryString = "SELECT new pl.fus.lego.DTOs.PartSubstituteDTO(pr.relType, pr.childPartNum, pr.parentPartNum) FROM PartRelationships pr WHERE pr.childPartNum IN :pnum OR pr.parentPartNum IN :pnum";

        TypedQuery<PartSubstituteDTO> query = entityManager.createQuery(queryString, PartSubstituteDTO.class);
        query.setParameter("pnum", partNums);

        List<PartSubstituteDTO> resultList = query.getResultList();
        Iterator<PartSubstituteDTO> iterator = resultList.iterator();
        while (iterator.hasNext()) {
            PartSubstituteDTO part = iterator.next();
            String relType = part.getRelType();

            if (!("M".equals(relType) || "A".equals(relType))) { //"P".equals(relType) ||
                iterator.remove(); // Remove the part from the list if it doesn't meet the conditions
            }
        }
        return resultList;
    }


    public String addSetToUser(CrudSetRequest request) {
        TypedQuery<UserSets> query = entityManager.createQuery(
                "SELECT us FROM UserSets us WHERE us.setNum = :setNum AND us.userId = :userId", UserSets.class);
        query.setParameter("setNum", request.getSetNum());
        query.setParameter("userId", request.getUserId());
        List<UserSets> results = query.getResultList();
        int result;
        if (!results.isEmpty()) {
            // Set exists, update the quantity
            String sql = "UPDATE user_sets SET quantity = quantity + 1 WHERE set_num = " + request.getSetNum() + "  AND user_id = " + request.getUserId();
            result = entityManager.createNativeQuery(sql).executeUpdate();
            System.out.println(result);
        } else {
            // Set does not exist, create a new one using native SQL
            String sql = "INSERT INTO user_sets (set_num, user_id, quantity) VALUES ( " + request.getSetNum() + ", " + request.getUserId() + ", 1);";
            result = entityManager.createNativeQuery(sql).executeUpdate();
            System.out.println(result);
        }
        if (result > 0) {
            System.out.println(result + " rows affected.");
        } else {
            System.out.println("No rows affected.");
        }
        return "Pomyślnie dodano zestaw";

    }

    public List<Object[]> findMinifigAndParts(String figNum) {
        String sql = "SELECT im.inventory_id ,ip.part_num ,ip.color_id, c.name, ip.quantity, ip.img_url FROM inventory_minifigs im " +
                "JOIN minifigs m ON m.fig_num = im.fig_num " +
                "LEFT JOIN inventories iv ON im.fig_num = iv.set_num " +
                "JOIN inventory_parts ip ON  iv.id = ip.inventory_id " +
                "JOIN colors c ON ip.color_id = c.id " +
                "WHERE im.fig_num = :figNum";
        Query queryParts = entityManager.createNativeQuery(sql);
        queryParts.setParameter("figNum", figNum);
        List<Object[]> results = queryParts.getResultList();
        return results;
    }

    public List<SetDTO> findSetsMinifigsIn(List<String> ivIdList) {
        String sqlSets = " SELECT DISTINCT (s.set_num), s.name, s.year, t.name, pt.name, s.num_parts, s.img_url FROM sets s " +
                "LEFT JOIN themes t ON s.theme_id = t.id " +
                "LEFT JOIN themes pt ON s.p_theme_id = pt.id " +
                "LEFT JOIN inventories iv ON s.set_num = iv.set_num " +
                "WHERE iv.id IN :invId";
        Query querySets = entityManager.createNativeQuery(sqlSets);
        querySets.setParameter("invId", ivIdList);
        List<Object[]> resultsSets = querySets.getResultList();
        ArrayList<SetDTO> setListFinIsIn = new ArrayList<>();
        SetDTO.SetDTOBuilder builder = SetDTO.builder();
        for (Object[] set : resultsSets) {
            setListFinIsIn.add(builder.setNum((String) set[0])
                    .name((String) set[1])
                    .year((Integer) set[2])
                    .themeName((String) set[3])
                    .parentThemeName((String) set[4])
                    .numParts((Integer) set[5])
                    .imgUrl((String) set[6]).build());
        }
        return setListFinIsIn;
    }

    public MinifigDTO findMinifigDetails(String figNum) {
        String sql = "SELECT distinct(m.fig_num), m.name, m.num_parts, m.img_url FROM inventory_minifigs im " +
                "JOIN minifigs m ON m.fig_num = im.fig_num " +
                "LEFT JOIN inventories iv ON im.fig_num = iv.set_num " +
                "JOIN inventory_parts ip ON  iv.id = ip.inventory_id " +
                "WHERE im.fig_num = :figNum";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("figNum", figNum);
        List<Object[]> results = query.getResultList();
        ArrayList<MinifigDTO> minifig = new ArrayList<>();
        MinifigDTO.MinifigDTOBuilder builder = MinifigDTO.builder();
        for (Object[] fig : results) {
            minifig.add(builder.figNum((String) fig[0]).name((String) fig[1]).numParts((Integer) fig[2]).imgUrl((String) fig[3]).build());
        }
        return minifig.get(0);
    }
}
