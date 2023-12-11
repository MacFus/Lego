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
import pl.fus.lego.Repositories.SetRepo;
import pl.fus.lego.UTILS.ApiResponse;
import pl.fus.lego.UTILS.Criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Service
public class SetService {
    private final SetRepo setRepo;
    private final SetMapper setMapper;
    private final InventoryPartsMapper inventoryPartsMapper;


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

    public ApiResponse<Object> findMyPartsToSet(Criteria criteria) {
        List<List<Object>> mySets = setRepo.findMyPartsToSet(criteria);
        Map<SetDTO, List<InventoryPartsDTO>> map = processList(mySets);
        return null;
    }

    public Map<SetDTO, List<InventoryPartsDTO>> processList(List<List<Object>> list) {
        Map<SetDTO, List<InventoryPartsDTO>> resultMap = new HashMap<>();
        List<InventoryPartsDTO> inventoryPartsList = new ArrayList<>();
        SetDTO currentSet = null;

        for (List<Object> row : list) {
            for (Object obj : row) {
                if (obj instanceof Sets) {
                    // Jeśli obiekt jest klasy Sets, ustaw go jako aktualny zestaw
                    currentSet = setMapper.map((Sets) obj);
                } else if (obj instanceof InventoryParts) {
                    // Jeśli obiekt jest klasy InventoryParts, dodaj go do listy Inventory Parts
                    InventoryPartsDTO partsDTO = inventoryPartsMapper.map(obj);
                    inventoryPartsList.add(partsDTO);

                    // Jeśli istnieje aktualny zestaw, dodaj go jako klucz do mapy
                    if (currentSet != null) {
                        resultMap.computeIfAbsent(currentSet, k -> new ArrayList<>())
                                .add(partsDTO);
                    }
                }
            }
        }

        return resultMap;
    }
}
