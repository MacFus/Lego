package pl.fus.lego;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fus.lego.DTOs.InventoryPartsDTO;
import pl.fus.lego.DTOs.MinifigDTO;
import pl.fus.lego.DTOs.SetDTO;
import pl.fus.lego.Entity.InventoryParts;
import pl.fus.lego.Services.MinifigService;
import pl.fus.lego.Services.SetService;
import pl.fus.lego.UTILS.ApiResponse;
import pl.fus.lego.UTILS.ApiResponseMap;
import pl.fus.lego.UTILS.Criteria;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class Controller {

    private final SetService setService;
    private final MinifigService minifigService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @RequestMapping(value = "/sets/{offset}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<SetDTO>> getSetsWithCriteria(@RequestBody Criteria criteria, @PathVariable int offset, @PathVariable int pageSize) {
        PageRequest pr = PageRequest.of(offset, pageSize).withSort(Sort.by(criteria.getSortField()));
        ApiResponse<SetDTO> setsWithPaginationAndCriteria = setService.findSetsWithPaginationAndCriteria(criteria, pr);
        return ResponseEntity.status(HttpStatus.OK).body(setsWithPaginationAndCriteria);
    }

    @RequestMapping(value = "/minifigs/{offset}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<MinifigDTO>> getMinifigsWithCriteria(@RequestBody Criteria criteria, @PathVariable int offset, @PathVariable int pageSize) {
        PageRequest pr = PageRequest.of(offset, pageSize).withSort(Sort.by(criteria.getSortField()));
        ApiResponse<MinifigDTO> minifigsWithPaginationAndCriteria = minifigService.findMinifigWithPaginationAndCriteria(criteria, pr);
        return ResponseEntity.status(HttpStatus.OK).body(minifigsWithPaginationAndCriteria);
    }

    @RequestMapping(value = "/myparts", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<InventoryParts>> getMyParts(@RequestBody Criteria criteria) {
        ApiResponse<InventoryParts> myParts = setService.findMyParts(criteria);
        return ResponseEntity.status(HttpStatus.OK).body(myParts);
    }

    @RequestMapping(value = "/mysets", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<SetDTO>> getMySets(@RequestBody Criteria criteria) {
        ApiResponse<SetDTO> myParts = setService.findMySets(criteria);
        return ResponseEntity.status(HttpStatus.OK).body(myParts);
    }

    @RequestMapping(value = "/mypartset", method = RequestMethod.GET)
    public ResponseEntity<ApiResponseMap<String, InventoryPartsDTO>> getMyPartsToSets(@RequestBody Criteria criteria) {
        ApiResponseMap<String, InventoryPartsDTO> myPartsToSet = setService.findMyPartsToSet(criteria);
        return ResponseEntity.status(HttpStatus.OK).body(myPartsToSet);
    }

    @RequestMapping(value = "/findSetsToDo/{offset}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponseMap<String, InventoryPartsDTO>> getAllSetsMapped(@RequestBody Criteria criteria, @PathVariable int offset, @PathVariable int pageSize) {
        PageRequest pr = PageRequest.of(offset, pageSize);
        ApiResponseMap<String, InventoryPartsDTO> myPartsToSet = setService.findPartsToSetList(criteria, pr);
        return ResponseEntity.status(HttpStatus.OK).body(myPartsToSet);
    }

}
