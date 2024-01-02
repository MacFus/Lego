package pl.fus.lego.Services;

import jakarta.servlet.http.Part;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import pl.fus.lego.DTOs.*;
import pl.fus.lego.Entity.User;
import pl.fus.lego.Entity.UserSets;
import pl.fus.lego.Mappers.InventoryPartsMapper;
import pl.fus.lego.Mappers.SetMapper;
import pl.fus.lego.Repositories.EntityRepository;
import pl.fus.lego.Repositories.MinifigRepo;
import pl.fus.lego.Repositories.SetRepo;
import pl.fus.lego.Repositories.UserSetRepo;
import pl.fus.lego.UTILS.*;

import java.util.*;

@Data
@org.springframework.stereotype.Service
public class Service {
    private final SetMapper setMapper;
    private final SetRepo setRepo;
    private final UserSetRepo userSetRepo;
    private final InventoryPartsMapper inventoryPartsMapper;
    private final EntityRepository repository;
    private final MinifigRepo minifigRepo;
    private final ApiService apiService;
    private Map<String, List<InventoryPartsDTO>> setPartsMap;

    /**
     * On login method retrieves user credentials and list of user sets
     * with all of its parts
     *
     * @param userId - Id
     * @return User credentials & user sets with params
     */
    public User getUserCredentials(Integer userId) {
        return repository.findUser(userId);
    }

    public ApiSetResponse getMySetsWithParts(Integer userId) {
        List<SetDTO> mySets = repository.findMySets(userId);
        List<String> mySetNums = mySets.stream().map(SetDTO::getSetNum).toList();
        List<PartDTO> myParts = repository.findPartsToSetList(mySetNums);
        Map<String, List<PartDTO>> setPartMap = new HashMap<>();
        int partsQuantity;
        for (SetDTO mySet : mySets) {
            partsQuantity = 0;
            if (mySet.getQuantity() > 1) {
                for (PartDTO myPart : myParts) {
                    if (mySet.getSetNum().equals(myPart.getSetNum())) {
                        Integer quantity = myPart.getQuantity();
                        myPart.setQuantity(mySet.getQuantity() * quantity);
                        setPartMap.computeIfAbsent(mySet.getSetNum(), k -> new ArrayList<>()).add(myPart);
                        partsQuantity += myPart.getQuantity();
                    }
                }
                mySet.setPartsQuantity(partsQuantity);
            } else {
                for (PartDTO myPart : myParts) {
                    if (mySet.getSetNum().equals(myPart.getSetNum())) {
                        setPartMap.computeIfAbsent(mySet.getSetNum(), k -> new ArrayList<>()).add(myPart);
                        partsQuantity += myPart.getQuantity();
                    }
                }
                mySet.setPartsQuantity(partsQuantity);
            }

        }
        return new ApiSetResponse(mySets, setPartMap);
    }

    public ApiResponse<SetDTO> getSetsWithCriteria(Criteria cr, int offset, int pageSize) {
        PageRequest pr = Utils.getPageRequest(cr, offset, pageSize);
        return repository.findSetsByCriteria(cr, pr);
    }

    public ApiResponse<MinifigDTO> getMinifigsWithCriteria(Criteria cr, int offset, int pageSize) {
        PageRequest pr = Utils.getPageRequest(cr, offset, pageSize);
        return repository.findMinifigsByCriteria(cr, pr);
    }

    public ApiResponse<SetDTO> getBuildSetsWithCriteria(Criteria cr, int offset, int pageSize) {
        PageRequest pr = Utils.getPageRequest(cr, offset, pageSize);
        List<SetDTO> mySets = repository.findMySets(cr.getUserId());
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
        Map<String, Integer> tempSetMap;
        int totalQuantity, counter = 0;
        double totalOwned, percentage;
        List<SetDTO> setsWithMatch = new ArrayList<>();
        List<SetDTO> allSets = repository.findSetsByCriteria(cr, pr).getResponse();
        for (String set_num : allSets.stream().map(SetDTO::getSetNum).toList()) {
            Map<String, Integer> myTempPartMap = new HashMap<>();
            for (Map.Entry<String, Integer> entry : myPartsMap.entrySet()) {
                myTempPartMap.put(entry.getKey(), (entry.getValue()));
            }
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
        return new ApiResponse<>(setsWithMatch, setsWithMatch.size());
    }

    public List<ThemeDTO> getThemes() {
        return repository.findThemes();
    }

    public ApiSetResponse getSetAndParts(String setNum) {
        List<SetDTO> sets = repository.findSetAndParts(setNum);
        List<String> setNumList = sets.stream().map(SetDTO::getSetNum).toList();
        List<PartDTO> parts = repository.findPartsToSetList(setNumList);
        List<String> partNumList = new ArrayList<>();
        Map<String, List<PartDTO>> setPartMap = new HashMap<>();
        int partsQuantity;
        for (SetDTO set : sets) {
            if (set.getQuantity() == null) set.setQuantity(0);
            if (set.getNumParts() == 0)
                continue;
            partsQuantity = 0;
            if (set.getQuantity() > 1) {
                for (PartDTO part : parts) {
                    if (set.getSetNum().equals(part.getSetNum())) {
                        Integer quantity = part.getQuantity();
                        part.setQuantity(set.getQuantity() * quantity);
                        setPartMap.computeIfAbsent(set.getSetNum(), k -> new ArrayList<>()).add(part);
                        partsQuantity += part.getQuantity();
                    }
                }
                set.setPartsQuantity(partsQuantity);
            } else {
                for (PartDTO part : parts) {
                    if (set.getSetNum().equals(part.getSetNum())) {
                        setPartMap.computeIfAbsent(set.getSetNum(), k -> new ArrayList<>()).add(part);
                        partsQuantity += part.getQuantity();
                    }
                }
                set.setPartsQuantity(partsQuantity);
            }

        }
        for (PartDTO part : parts) {
            partNumList.add(part.getPartNum());
        }
        List<PartSubstituteDTO> substitutes = repository.findSubstitutes(partNumList);
        return new ApiSetResponse(sets, setPartMap, substitutes);
    }

    public List<UserSets> addSetToUser(CrudSetRequest request) {
        List<UserSets> savedUserSets = new ArrayList<>();
        for (String setNum : request.getSetNum()) {
            Optional<UserSets> userSet = userSetRepo.findUserSetsBySetNumAndUserId(setNum, request.getUserId());
            if (userSet.isEmpty()) {
                savedUserSets.add(userSetRepo.save(new UserSets(setNum, request.getUserId(), 1)));
            } else {
                UserSets set = userSet.get();
                int quantity = set.getQuantity();
                set.setQuantity(quantity + 1);
                savedUserSets.add(userSetRepo.save(set));
            }
        }
        return savedUserSets;
    }

    public List<String> deleteSetFromUser(CrudSetRequest request) {
        List<String> removedUserSets = new ArrayList<>();
        for (String setNum : request.getSetNum()) {
            Optional<UserSets> userSet = userSetRepo.findUserSetsBySetNumAndUserId(setNum, request.getUserId());
            if (userSet.isEmpty()) {
                removedUserSets.add("Użytkownik nie posiada takiego zestawu: " + setNum);
            } else if (request.getAll()) {
                UserSets set = userSet.get();
                userSetRepo.delete(set);
                removedUserSets.add("Zestaw " + setNum + " został usunięty");
            } else {
                UserSets set = userSet.get();
                int quantity = set.getQuantity();
                set.setQuantity(quantity - 1);
                if (set.getQuantity() == 0) {
                    userSetRepo.delete(set);
                    removedUserSets.add("Zestaw " + setNum + " został usunięty");
                } else {
                    userSetRepo.save(set);
                    removedUserSets.add("Zestaw " + setNum + " został pomniejszony o jeden");
                }
            }
        }
        return removedUserSets;
    }

    public ApiMinifigResponse getMinifigAndParts(String figNum) {
        List<Object[]> parts = repository.findMinifigAndParts(figNum);
        PartDTO.PartDTOBuilder builder = PartDTO.builder();
        List<String> ivIdList = new ArrayList<>();
        ArrayList<PartDTO> partDTOS = new ArrayList<>();
        for (Object[] part : parts) {
            if (!ivIdList.contains((String) part[0])) {
                ivIdList.add((String) part[0]);
            }
            if (!ivIdList.contains((String) part[0])) {
                ivIdList.add((String) part[0]);
            }
            partDTOS.add(builder
                    .partNum((String) part[1])
                    .colorId((Integer) part[2])
                    .colorName((String) part[3])
                    .quantity((Integer) part[4])
                    .imgUrl((String) part[5])
                    .build());
        }
        //SELECT im.inventory_id ,ip.part_num ,ip.color_id, c.name, ip.quantity, ip.img_url FROM
        Map<String, PartDTO> map = new LinkedHashMap<>(); // LinkedHashMap preserves the insertion order
        for (PartDTO obj : partDTOS) {
            map.putIfAbsent(obj.getPartNum(), obj);
        }
        List<PartDTO> minifigParts = new ArrayList<>(map.values());
        List<SetDTO> setsMinifigIn = repository.findSetsMinifigsIn(ivIdList);
        MinifigDTO minifig = repository.findMinifigDetails(figNum);
        System.out.println();
        return new ApiMinifigResponse(minifig, setsMinifigIn, minifigParts);
//        List<String> setNumList = minifigs.stream().map(SetDTO::getSetNum).toList();
//        List<PartDTO> parts = repository.findPartsToSetList(setNumList);
//        List<String> partNumList = new ArrayList<>();
//        Map<String, List<PartDTO>> setPartMap = new HashMap<>();
//        int partsQuantity;
//        for (SetDTO set : sets) {
//            if (set.getQuantity() == null) set.setQuantity(0);
//            if (set.getNumParts() == 0)
//                continue;
//            partsQuantity = 0;
//            if (set.getQuantity() > 1) {
//                for (PartDTO part : parts) {
//                    if (set.getSetNum().equals(part.getSetNum())) {
//                        Integer quantity = part.getQuantity();
//                        part.setQuantity(set.getQuantity() * quantity);
//                        setPartMap.computeIfAbsent(set.getSetNum(), k -> new ArrayList<>()).add(part);
//                        partsQuantity += part.getQuantity();
//                    }
//                }
//                set.setPartsQuantity(partsQuantity);
//            } else {
//                for (PartDTO part : parts) {
//                    if (set.getSetNum().equals(part.getSetNum())) {
//                        setPartMap.computeIfAbsent(set.getSetNum(), k -> new ArrayList<>()).add(part);
//                        partsQuantity += part.getQuantity();
//                    }
//                }
//                set.setPartsQuantity(partsQuantity);
//            }
//
//        }
//        for (PartDTO part : parts) {
//            partNumList.add(part.getPartNum());
//        }
//        List<PartSubstituteDTO> substitutes = repository.findSubstitutes(partNumList);
//        return new ApiSetResponse(sets, setPartMap, substitutes);
    }
}
