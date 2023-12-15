package pl.fus.lego.Services;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.fus.lego.DTOs.InventoryPartsDTO;
import pl.fus.lego.DTOs.MinifigDTO;
import pl.fus.lego.DTOs.PartDTO;
import pl.fus.lego.DTOs.SetDTO;
import pl.fus.lego.Entity.Minifigs;
import pl.fus.lego.Entity.Sets;
import pl.fus.lego.Mappers.InventoryPartsMapper;
import pl.fus.lego.Mappers.SetMapper;
import pl.fus.lego.Repositories.EntityRepository;
import pl.fus.lego.Repositories.MinifigRepo;
import pl.fus.lego.Repositories.SetRepo;
import pl.fus.lego.UTILS.ApiResponse;
import pl.fus.lego.UTILS.ApiResponseParts;
import pl.fus.lego.UTILS.Criteria;
import pl.fus.lego.UTILS.Utils;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Service
public class SetService {
    private final SetRepo setRepo;
    private final SetMapper setMapper;
    private final InventoryPartsMapper inventoryPartsMapper;
    private final EntityRepository repository;
    private final MinifigRepo minifigRepo;

    private Map<String, List<InventoryPartsDTO>> setPartsMap;

    public ApiResponse<SetDTO> getSetsWithCriteria(Criteria cr, int offset, int pageSize) {
        PageRequest pr = Utils.getPageRequest(cr, offset, pageSize);
        Page<Sets> sets = setRepo.findSetsByCriteria(cr, pr);
        System.out.println(cr);
        List<SetDTO> setDTOList = sets.getContent().stream()
                .map(setMapper::map)
                .toList();
//        return new ApiResponse<>(setDTOList, setDTOList.size());
//        List<SetDTO> setDTOS = repository.findSetsByCriteria(cr, pr);
        return new ApiResponse<>(setDTOList, setDTOList.size());
    }

    public ApiResponse<MinifigDTO> getMinifigsWithCriteria(Criteria cr, int offset, int pageSize){
        PageRequest pr = Utils.getPageRequest(cr, offset, pageSize);
        Page<Minifigs> minifigs = minifigRepo.findMinifigsByCriteria(cr, pr);
        System.out.println(cr);
        List<MinifigDTO> minifigDTOList = null;
        System.out.println(minifigs);
        return new ApiResponse<>(minifigDTOList, minifigDTOList.size());
    }

    public ApiResponse<SetDTO> getMySets(Criteria cr) {
        List<SetDTO> sets = repository.findMySets(cr);
        return new ApiResponse<>(sets, sets.size());
    }

    public ApiResponse<PartDTO> getMyParts(Criteria cr) {
        List<String> listOfSetNum = repository.findMySets(cr).stream().map(SetDTO::getSetNum).toList();
        List<InventoryPartsDTO> inventoryParts = repository.findPartsToSetList(listOfSetNum, cr.getUserId());
        List<PartDTO> parts = mapInventoryPartsToParts(inventoryParts);
        int allParts = parts.stream()
                .mapToInt(PartDTO::getQuantity)
                .sum();
        return new ApiResponse<>(parts.size(), parts, allParts);
    }

    public ApiResponseParts<PartDTO> getPartsToSet(Criteria cr, String setNum) {

        return null;
    }

    //UTILS


//    public ApiResponseParts<PartDTO> getMyParts(Criteria cr) {
//        List<String> listOfSetNum = repository.findMySets(cr).stream().map(SetDTO::getSetNum).toList();
//        List<InventoryPartsDTO> inventoryParts = repository.findPartsToSetList(listOfSetNum, cr.getUserId());
//        List<PartDTO> parts = mapInventoryPartsToParts(inventoryParts);
//        int allParts = parts.stream()
//                .mapToInt(PartDTO::getQuantity)
//                .sum();
//        return new ApiResponseParts<>(parts.size(), allParts, parts);
//    }
//
//    public void getPercentageOfSets(Criteria cr, int offset, int pageSize){
//        PageRequest pr = Utils.getPageRequest(cr, offset, pageSize);
//        Page<Sets> sets;
//        if (cr.getTheme() != null) {
//            sets = setRepo.findSetsByCriteria(cr, pr);
//        } else {
//            sets = setRepo.findSetsNoTheme(cr, pr);
//        }
//        List<String> setNumList = sets.getContent().stream()
//                .map(Sets::getSetNum)
//                .collect(Collectors.toList());
//        List<InventoryPartsDTO> inventoryParts = repository.findPartsToSetList(setNumList);
//        List<PartDTO> parts = mapInventoryPartsToParts(inventoryParts);
//        Map<String, List<PartDTO>> stringListMap = mapSetNumToListOfParts(parts);
//        System.out.println();
//
//    }

    private Map<String, List<PartDTO>> mapSetNumToListOfParts(List<PartDTO> parts) {
        return parts.stream()
                .collect(Collectors.groupingBy(PartDTO::getSetNum));
    }

    private List<PartDTO> mapInventoryPartsToParts(List<InventoryPartsDTO> inventoryParts) {
        List<PartDTO> parts = new ArrayList<>();
        for (InventoryPartsDTO ip : inventoryParts) {
            if (ip.getSetQuantity() == null)
                ip.setSetQuantity(1);
            PartDTO part = new PartDTO(ip.getSetName(), ip.getSetNum(), ip.getImgUrl(),
                    ip.getPartNum(), ip.getSetQuantity() * ip.getQuantityStr(), ip.getColorId(), ip.getIsSpare());
            parts.add(part);
        }
        return parts;
    }


//    public ApiResponseMap<String, InventoryPartsDTO> findMyPartsToSet(Criteria cr) {
//        List<List<Object>> mySets = setRepo.findMyPartsToSet(cr);
//        Map<String, List<InventoryPartsDTO>> map = processList(mySets);
//        return new ApiResponseMap<>(map.size(), map);
//    }
//
//    public ApiResponseMap<String, InventoryPartsDTO> mapAllSetsWithParts(int offset, int pageSize) {
//        List<List<Object>> mySets = setRepo.getAllSetsWithParts(pr);
//        Map<String, List<InventoryPartsDTO>> map = processList(mySets);
//        return new ApiResponseMap<>(map.size(), map);
//    }

//    public ApiResponseMap<String, InventoryPartsDTO> findPartsToSetList(Criteria cr, int offset, int pageSize) {
//        Page<Sets> sets;
//        List<SetDTO> entitySets;
//        if (cr.getTheme() != null) {
//            sets = setRepo.findSetsByCriteria(cr, pr);
//            entitySets = repository.findSetsByCriteria(cr, pr);
//        } else {
//            sets = setRepo.findSetsNoTheme(cr, pr);
//            entitySets = repository.findAllSets(cr, pr);
//        }
//        List<String> setNumList = sets.stream().map(Sets::getSetNum).toList();
//        List<List<Object>> partsToSetList = setRepo.findPartsToSetList(setNumList);
//        Map<String, List<InventoryPartsDTO>> list = processList(partsToSetList);
//        return null;
//    }

//    public ApiResponseMap<String, InventoryPartsDTO> findPartsToSetList(Criteria cr, int offset, int pageSize) {
//        List<SetDTO> sets;
//        if (cr.getCriteria() != true)
//            sets = repository.findAllSets(cr, pr);
//        else
//            sets = repository.findSetsByCriteria(cr, pr);
//
//        //find Parts to Sets
//        List<String> listOfSetNum = getArrayOfSetNum(sets);
//        List<InventoryPartsDTO> partsToSetList = repository.findPartsToSetList(listOfSetNum);
////        List<InventoryParts> inventoryPartsForSets = repository.getInventoryPartsForSets(listOfSetNum);
//        Map<String, List<InventoryPartsDTO>> mapPartsToSet = mapPartsToSet(partsToSetList);
//        return null;
//    }

    private Map<String, List<InventoryPartsDTO>> mapPartsToSet(List<InventoryPartsDTO> list) {

        Map<String, List<InventoryPartsDTO>> map = list.stream()
                .collect(Collectors.groupingBy(
                        InventoryPartsDTO::getSetNum,
                        Collectors.toList()          // Collecting as a list
                ));
        return map;
    }

    public void getSetParts(String setNum) {
    }

}
