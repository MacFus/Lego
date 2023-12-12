package pl.fus.lego.Services;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.fus.lego.DTOs.InventoryPartsDTO;
import pl.fus.lego.DTOs.SetDTO;
import pl.fus.lego.Entity.InventoryParts;
import pl.fus.lego.Entity.Sets;
import pl.fus.lego.Mappers.InventoryPartsMapper;
import pl.fus.lego.Mappers.SetMapper;
import pl.fus.lego.Repositories.EntityRepository;
import pl.fus.lego.Repositories.SetRepo;
import pl.fus.lego.UTILS.ApiResponse;
import pl.fus.lego.UTILS.ApiResponseMap;
import pl.fus.lego.UTILS.Criteria;

import java.util.*;

@Data
@Service
public class SetService {
    private final SetRepo setRepo;
    private final SetMapper setMapper;
    private final InventoryPartsMapper inventoryPartsMapper;
    private final EntityRepository repository;

    private Map<String, List<InventoryPartsDTO>> setPartsMap;


    public ApiResponse<SetDTO> findSetsWithPaginationAndCriteria(Criteria criteria, PageRequest pr) {
        System.out.println(criteria);
        Page<Sets> sets;
        if (criteria.getTheme() != null) {
            sets = setRepo.findSetsByCriteria(criteria, pr);
        } else {
            sets = setRepo.findSetsNoTheme(criteria, pr);
        }
        List<SetDTO> setDTOList = sets.getContent().stream()
                .map(setMapper::map)
                .toList();
        return new ApiResponse<>(setDTOList.size(), setDTOList);
    }

    public ApiResponse<InventoryParts> findMyParts(Criteria criteria) {
        List<InventoryParts> myParts = setRepo.findMyParts(criteria);
//        Page<Sets> mySets = setRepo.findMySets(criteria);

        List<InventoryParts> myPartsList = myParts.stream().toList();
//        List<MySets> setDTOList = sets.getContent().stream()
//                .map(setMapper::map)
//                .toList();
        return new ApiResponse<>(myPartsList.size(), myPartsList);
    }

    public ApiResponse<SetDTO> findMySets(Criteria criteria) {
        List<Sets> mySets;
        mySets = setRepo.findMySets(criteria);
        List<SetDTO> setDTOList = mySets.stream()
                .map(setMapper::map)
                .toList();
        return new ApiResponse<>(setDTOList.size(), setDTOList);
    }

    public ApiResponseMap<String, InventoryPartsDTO> findMyPartsToSet(Criteria criteria) {
        List<List<Object>> mySets = setRepo.findMyPartsToSet(criteria);
        Map<String, List<InventoryPartsDTO>> map = processList(mySets);
        return new ApiResponseMap<>(map.size(), map);
    }

    public ApiResponseMap<String, InventoryPartsDTO> mapAllSetsWithParts(PageRequest pr) {
        List<List<Object>> mySets = setRepo.getAllSetsWithParts(pr);
        Map<String, List<InventoryPartsDTO>> map = processList(mySets);
        return new ApiResponseMap<>(map.size(), map);
    }

//    public ApiResponseMap<String, InventoryPartsDTO> findPartsToSetList(Criteria criteria, PageRequest pr) {
//        Page<Sets> sets;
//        if (criteria.getTheme() != null) {
//            sets = setRepo.findSetsByCriteria(criteria, pr);
//        } else {
//            sets = setRepo.findSetsNoTheme(criteria, pr);
//        }
//        List<String> setNumList = sets.stream().map(Sets::getSetNum).toList();
//        List<List<Object>> partsToSetList = setRepo.findPartsToSetList(setNumList);
//        Map<String, List<InventoryPartsDTO>> list = processList(partsToSetList);
//        return null;
//    }

    public ApiResponseMap<String, InventoryPartsDTO> findPartsToSetList(Criteria criteria, PageRequest pr) {
        // find all sets by criteria and page
//        List<InventoryPartsDTO> starWars = repository.findAllWithTheme("Star Wars");
        List<SetDTO> starWars = repository.findSetsByCriteria(criteria, pr);
        return null;
    }

    public Map<String, List<InventoryPartsDTO>> processList(List<List<Object>> list) {
        Map<String, List<InventoryPartsDTO>> resultMap = new TreeMap<>();
        List<InventoryPartsDTO> inventoryPartsList = new ArrayList<>();
        String currentSetName = null;
        InventoryPartsDTO partsDTO = null;
        for (List<Object> row : list) {
            System.out.println();
            for (int i = 0; i < row.size(); i++) {
                if (i == 0)
                    currentSetName = String.valueOf(row.get(0));
                else
                    partsDTO = inventoryPartsMapper.map(row.get(1));
            }

            if (inventoryPartsList.contains(partsDTO)) {
                int index = inventoryPartsList.indexOf(partsDTO);
                Integer quantity = inventoryPartsList.get(index).getQuantity() + partsDTO.getQuantity();
                inventoryPartsList.get(index).setQuantity(quantity);
                continue;
            }
            inventoryPartsList.add(partsDTO);

            if (currentSetName != null) {
                resultMap.computeIfAbsent(currentSetName, k -> new ArrayList<>())
                        .add(partsDTO);
                System.out.println();
            }

        }
        return resultMap;

    }
}
