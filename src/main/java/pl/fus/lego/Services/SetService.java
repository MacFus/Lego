package pl.fus.lego.Services;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.fus.lego.DTOs.InventoryPartsDTO;
import pl.fus.lego.DTOs.MinifigDTO;
import pl.fus.lego.DTOs.PartDTO;
import pl.fus.lego.DTOs.SetDTO;
import pl.fus.lego.Entity.Sets;
import pl.fus.lego.Mappers.InventoryPartsMapper;
import pl.fus.lego.Mappers.SetMapper;
import pl.fus.lego.Repositories.EntityRepository;
import pl.fus.lego.Repositories.MinifigRepo;
import pl.fus.lego.Repositories.SetRepo;
import pl.fus.lego.UTILS.*;

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
    private final ApiService apiService;

    private Map<String, List<InventoryPartsDTO>> setPartsMap;

    public ApiResponse<SetDTO> getSetsWithCriteria(Criteria cr, int offset, int pageSize) {
        PageRequest pr = Utils.getPageRequest(cr, offset, pageSize);
        Page<Sets> sets = setRepo.findSetsByCriteria(cr, pr);
        List<SetDTO> setDTOList = sets.getContent().stream()
                .map(setMapper::map)
                .toList();
        return new ApiResponse<>(setDTOList, setDTOList.size());
    }

    public ApiResponse<MinifigDTO> getMinifigsWithCriteria(Criteria cr, int offset, int pageSize) {
        PageRequest pr = Utils.getPageRequest(cr, offset, pageSize);
        List<MinifigDTO> minifigs = repository.findMinifigsByCriteria(cr, pr);
        return new ApiResponse<>(minifigs, minifigs.size());
    }

    public ApiResponse<SetDTO> getBuildSetsWithCriteria(Criteria cr, int offset, int pageSize) {
        PageRequest pr = Utils.getPageRequest(cr, offset, pageSize);
//        ApiResponse<PartDTO> myParts = getMyParts(cr);
        List<SetDTO> mySets = repository.findMySets(cr);
        List<PartDTO> myParts = repository.findPartsToSetList(mySets.stream().map(SetDTO::getSetNum).toList());
        Map<String, Integer> myPartsMap = new HashMap<>();
        for (SetDTO mySet : mySets) {
            if (mySet.getQuantity() > 1) {
                for (PartDTO myPart : myParts) {
                    if (mySet.getSetNum().equals(myPart.getSetNum())) {
                        Integer quantity = myPart.getQuantity();
                        myPart.setQuantity(mySet.getQuantity() * quantity);
                        myPartsMap.put(myPart.getPartNum(), myPart.getQuantity());
                    }
                }
            }
        }
        Map<String, Integer> tempSetMap = null;
        int totalQuantity, counter = 0;
        double totalOwned, percentage;
        List<SetDTO> setsWithMatch = new ArrayList<>();
        List<SetDTO> allSets = repository.findSetsByCriteria(cr, pr);
        for (String set_num : allSets.stream().map(SetDTO::getSetNum).toList()) {
            int mySum = 0;
            Map<String, Integer> myTempPartMap = new HashMap<>();
            for (Map.Entry<String, Integer> entry : myPartsMap.entrySet()) {
                myTempPartMap.put(entry.getKey(), (entry.getValue()));
                mySum += entry.getValue();
            }
//            myTempPartMap = myPartsMap;
            percentage = 0.0;
            totalQuantity = 0;
            totalOwned = 0.0;
            tempSetMap = new HashMap<>();
            List<PartDTO> partsToSet = repository.findPartsToSetList(List.of(set_num));
            if (partsToSet.size() == 0)
                continue;
            for (PartDTO dto : partsToSet) {
                if (dto.getIsSpare().startsWith("t"))
                    continue;
                String partNum = dto.getPartNum();
                Integer quantity = dto.getQuantity();
                if (tempSetMap.containsKey(partNum)) {
                    Integer prevQuantity = tempSetMap.get(partNum);
                    tempSetMap.put(partNum, prevQuantity + quantity);
                } else
                    tempSetMap.put(partNum, quantity);
                totalQuantity += dto.getQuantity();
            }
            System.out.println();

//             porównywanie
            System.out.println("SET: " + set_num);
            for (String key : tempSetMap.keySet()) {
                if (myTempPartMap.containsKey(key)) {
                    Integer myValue = myTempPartMap.get(key);
                    Integer tempValue = tempSetMap.get(key);
//                    System.out.println("KEY: " + key + " ON VALUES " + myValue + " SAME WITH " + tempSetMap.get(key));
                    if (myValue >= tempValue) {
                        totalOwned += tempValue;
                        myTempPartMap.put(key, myValue - tempValue);

                    } else if (myValue < tempValue && myValue > 0) {
                        totalOwned += myValue;
                        myTempPartMap.remove(key);
                    } else {
                        totalOwned += myValue;
                        myTempPartMap.remove(key);
                    }
                }
            }
            System.out.println();
            percentage = totalOwned / totalQuantity * 100;
            if (percentage >= cr.getOwnPerc()) {
                allSets.get(counter).setMatch(percentage);

                setsWithMatch.add(allSets.get(counter));
            }
            counter++;
        }


//        List<SetDTO> setDTOS = setRepo.findSetsByCriteria(cr, pr).getContent().stream()
//                .map(setMapper::map)
//                .toList();
//        List<String> setNumList = setDTOS.stream().map(SetDTO::getSetNum).toList();
//        countMatchPercentage(myParts.getResponse(), setNumList, cr);

        return new ApiResponse<SetDTO>(setsWithMatch, setsWithMatch.size());
    }

    public static double calculateOwnedPercentage(Map<String, Integer> myPartsMap, Map<String, Integer> tempSetMap) {
        int ownedPieces = 0;
        int totalPieces = 0;

        for (Map.Entry<String, Integer> entry : tempSetMap.entrySet()) {
            String key = entry.getKey();
            int requiredQuantity = entry.getValue();
            totalPieces += requiredQuantity;

            if (myPartsMap.containsKey(key)) {
                int ownedQuantity = myPartsMap.get(key);
                int count = Math.min(ownedQuantity, requiredQuantity);
                ownedPieces += count;

                // Aktualizuj ilość w myPartsMap
                myPartsMap.put(key, ownedQuantity - count);

                // Usuń klucz, jeśli ilość spadła do zera
                if (myPartsMap.get(key) == 0) {
                    myPartsMap.remove(key);
                }
            }
        }

        return (double) ownedPieces / totalPieces * 100;
    }

    private Map<String, Double> countMatchPercentage(List<PartDTO> myParts, List<String> setNumList, Criteria cr) {
        @Data
        class TempPart {
            String partNum;
            Integer quantity;
            Double percentage;
            String isSpare;

            public TempPart(String partNum, Integer quantity, String isSpare) {
                this.partNum = partNum;
                this.quantity = quantity;
                this.isSpare = isSpare;
            }

            public TempPart(String partNum, Integer quantity) {
                this.partNum = partNum;
                this.quantity = quantity;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                TempPart tempPart = (TempPart) o;
                return partNum.equals(tempPart.partNum);
            }

            @Override
            public int hashCode() {
                return Objects.hash(partNum);
            }
        }
        HashMap<String, Double> map = new HashMap<>();
        List<TempPart> myTempParts = new ArrayList<>();
        for (PartDTO myPart : myParts) {
            myTempParts.add(new TempPart(myPart.getPartNum(), myPart.getQuantity(), myPart.getIsSpare()));
        }

        for (String set : setNumList) {

            List<TempPart> tempParts = new ArrayList<>();
            double sum = 0;
            ApiResponse parts = apiService.getParts(set);

            if (parts == null)
                continue;
            else if (parts.getIsEmbedded())
                continue;
//            if (parts.getResponse() == null) {
//                PartDTO tempPart = null;
//                for (Object part : parts.getResults()) {
//                    Result result = (Result) part;
//                    tempPart = result.getPart();
//                    if(tempPart.getIsSpare().startsWith("t"))
//                        continue;
//                    tempPart.setSetNum(result.getSetNum());
//                    tempParts.add(new TempPart(tempPart.getPartNum(), tempPart.getQuantity(), myPart.getIsSpare()));
//                }
//                sum = tempParts.stream().mapToInt(TempPart::getQuantity).sum();
//            } else {
            for (Object part : parts.getResponse()) {
                PartDTO result = (PartDTO) part;
                if (Objects.equals(result.getSetNum(), "30052-1")) {
                    System.out.println();
                }
                tempParts.add(new TempPart(result.getPartNum(), result.getQuantity()));
//                }
                sum = tempParts.stream().mapToInt(TempPart::getQuantity).sum();
            }
            int howManyOwned = 0;

            for (TempPart part : tempParts) {

                for (TempPart myTempPart : myTempParts) {
                    if (part.equals(myTempPart)) {
                        if (myTempPart.quantity >= part.quantity) {
                            howManyOwned += part.quantity;
                            break;
                        } else {
                            howManyOwned += myTempPart.quantity;
                            break;
                        }
                    }
                }
            }
            double ownPercentage = (howManyOwned / sum) * 100;
            System.out.println();
            if (ownPercentage > 100)
                map.put(set, 100.0);
            if (ownPercentage >= cr.getOwnPerc())
                map.put(set, ownPercentage);

        }
        return map;
    }

    public ApiResponse<SetDTO> getMySets(Criteria cr) {
        List<SetDTO> sets = repository.findMySets(cr);
        return new ApiResponse<>(sets, sets.size());
    }

    public List<Object> getMySets() {
//        List<SetDTO> sets = repository.findMySets(cr);
        return setRepo.findSetsInventory();
//        return new ApiResponse<>(sets, sets.size());
    }

//    public ApiResponse<PartDTO> getMyParts(Criteria cr) {
//        List<String> listOfSetNum = repository.findMySets(cr).stream().map(SetDTO::getSetNum).toList();
//        List<InventoryPartsDTO> inventoryParts = repository.findPartsToSetList(listOfSetNum, cr.getUserId());
//        List<PartDTO> parts = mapInventoryPartsToParts(inventoryParts);
//        int allParts = parts.stream()
//                .mapToInt(PartDTO::getQuantity)
//                .sum();
//        return new ApiResponse<>(parts, parts.size(), allParts);
//    }

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
                    ip.getPartNum(), ip.getSetQuantity() * ip.getQuantityStr(), Integer.valueOf(ip.getColorId()), ip.getIsSpare());
            parts.add(part);
        }
        return parts;
    }


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

    @Data
    class TempPart {
        String partNum;
        Integer quantity;
        Double percentage;
        String isSpare;

        public TempPart(String partNum, Integer quantity, String isSpare) {
            this.partNum = partNum;
            this.quantity = quantity;
            this.isSpare = isSpare;
        }

        public TempPart(String partNum, Integer quantity) {
            this.partNum = partNum;
            this.quantity = quantity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TempPart tempPart = (TempPart) o;
            return partNum.equals(tempPart.partNum);
        }

        @Override
        public int hashCode() {
            return Objects.hash(partNum);
        }
    }

}
