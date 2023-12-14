package pl.fus.lego;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fus.lego.DTOs.MinifigDTO;
import pl.fus.lego.DTOs.PartDTO;
import pl.fus.lego.DTOs.SetDTO;
import pl.fus.lego.Services.ApiService;
import pl.fus.lego.Services.MinifigService;
import pl.fus.lego.Services.SetService;
import pl.fus.lego.UTILS.ApiResponse;
import pl.fus.lego.UTILS.ApiResponseParts;
import pl.fus.lego.UTILS.Criteria;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class Controller {

    private final SetService setService;
    private final MinifigService minifigService;
    private final ApiService apiService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @RequestMapping(value = "/sets/{offset}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<SetDTO>> getSetsWithCriteria(@RequestBody Criteria cr, @PathVariable int offset, @PathVariable int pageSize) {
        ApiResponse<SetDTO> sets = setService.getSetsWithCriteria(cr, offset, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(sets);
    }

    @RequestMapping(value = "/minifigs/{offset}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<MinifigDTO>> getMinifigsWithCriteria(@RequestBody Criteria cr, @PathVariable int offset, @PathVariable int pageSize) {
        ApiResponse<MinifigDTO> minifigs = minifigService.getMinifigsWithCriteria(cr, offset, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(minifigs);
    }

    @RequestMapping(value = "/mysets/{offset}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<SetDTO>> getMySets(@RequestBody Criteria cr) {
        ApiResponse<SetDTO> myParts = setService.getMySets(cr);
        return ResponseEntity.status(HttpStatus.OK).body(myParts);
    }


//    @RequestMapping(value = "/build/find/{offset}/{pageSize}", method = RequestMethod.GET)
//    public void getAllSetsMapped(@RequestBody Criteria cr, @PathVariable int offset, @PathVariable int pageSize) {
//        setService.getPercentageOfSets(cr, offset, pageSize);
////        return ResponseEntity.status(HttpStatus.OK).body(myPartsToSet);
//    }
    @RequestMapping(value = "/myparts", method = RequestMethod.GET)
    public ResponseEntity<ApiResponseParts<PartDTO>> getMyParts(@RequestBody Criteria cr) {
        ApiResponseParts<PartDTO> myParts = setService.getMyParts(cr);
        return ResponseEntity.status(HttpStatus.OK).body(myParts);
    }

    @RequestMapping(value = "/sets/{setNum}/parts", method = RequestMethod.GET)
    public ResponseEntity<Object> getParts(@PathVariable String setNum) {
        Object response = apiService.getParts(setNum);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }


//    @RequestMapping(value = "/mypartset", method = RequestMethod.GET)
//    public ResponseEntity<ApiResponseMap<String, InventoryPartsDTO>> getMyPartsToSets(@RequestBody Criteria cr) {
//        ApiResponseMap<String, InventoryPartsDTO> myPartsToSet = setService.findMyPartsToSet(cr);
//        return ResponseEntity.status(HttpStatus.OK).body(myPartsToSet);
//    }


}
