package pl.fus.lego;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fus.lego.DTOs.MinifigDTO;
import pl.fus.lego.DTOs.PartDTO;
import pl.fus.lego.DTOs.SetDTO;
import pl.fus.lego.Services.ApiService;
import pl.fus.lego.Services.SetService;
import pl.fus.lego.UTILS.ApiResponse;
import pl.fus.lego.UTILS.Criteria;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/v1")
public class Controller {

    private final SetService setService;
    private final ApiService apiService;

    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public SetDTO sayHello() {
        return new SetDTO("1-num");
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping(value = "/sets/{offset}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<SetDTO>> getSetsWithCriteria(@PathVariable int offset, @PathVariable int pageSize,
                                                                   @RequestParam(required = false, name = "sYear") Integer sYear,
                                                                   @RequestParam(required = false, name = "eYear") Integer eYear,
                                                                   @RequestParam(required = false, name = "min") Integer min,
                                                                   @RequestParam(required = false, name = "max") Integer max,
                                                                   @RequestParam(required = false, name = "theme") String theme,
                                                                   @RequestParam(required = false, name = "sortBy", defaultValue = "name") String sortField,
                                                                   @RequestParam(required = false, name = "dir", defaultValue = "ASC") String dir,
                                                                   @RequestParam(required = false, name = "search") String search) {
        ApiResponse<SetDTO> sets = setService.getSetsWithCriteria(new Criteria(sYear, eYear, min, max, theme, sortField, dir, search), offset, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(sets);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping(value = "/minifigs/{offset}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<MinifigDTO>> getMinifigsWithCriteria(@PathVariable int offset, @PathVariable int pageSize,
                                                                           @RequestParam(required = false, name = "sYear") Integer sYear,
                                                                           @RequestParam(required = false, name = "eYear") Integer eYear,
                                                                           @RequestParam(required = false, name = "min") Integer min,
                                                                           @RequestParam(required = false, name = "max") Integer max,
                                                                           @RequestParam(required = false, name = "theme") String theme,
                                                                           @RequestParam(required = false, name = "sortBy", defaultValue = "name") String sortField,
                                                                           @RequestParam(required = false, name = "dir", defaultValue = "ASC") String dir,
                                                                           @RequestParam(required = false, name = "search") String search) {
        ApiResponse<MinifigDTO> minifigs = setService.getMinifigsWithCriteria(new Criteria(sYear, eYear, min, max, theme, sortField, dir, search), offset, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(minifigs);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping(value = "/build/{offset}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<SetDTO>> getBuildSetsWithCriteria(@PathVariable int offset, @PathVariable int pageSize,
                                                                        @RequestParam(required = false, name = "sYear") Integer sYear,
                                                                        @RequestParam(required = false, name = "eYear") Integer eYear,
                                                                        @RequestParam(required = false, name = "min") Integer min,
                                                                        @RequestParam(required = false, name = "max") Integer max,
                                                                        @RequestParam(required = false, name = "theme") String theme,
                                                                        @RequestParam(required = false, name = "sortBy", defaultValue = "name") String sortField,
                                                                        @RequestParam(required = false, name = "dir", defaultValue = "ASC") String dir,
                                                                        @RequestParam(required = false, name = "search") String search,
                                                                        @RequestParam(required = false, name = "own") Integer own,
                                                                        @RequestParam(required = false, name = "user") Integer user) {
        // SetWithMatchDTO
        ApiResponse<SetDTO> setsWithMatch = (ApiResponse<SetDTO>) setService.getBuildSetsWithCriteria(new Criteria(sYear, eYear, min, max, theme, sortField, dir, search, user, own), offset, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(setsWithMatch);
    }

//    @RequestMapping(value = "/mysets/{offset}/{pageSize}", method = RequestMethod.GET)
//    public ResponseEntity<ApiResponse<SetDTO>> getMySets(@RequestBody Criteria cr) {
//        ApiResponse<SetDTO> myParts = setService.getMySets(cr);
//        return ResponseEntity.status(HttpStatus.OK).body(myParts);
//    }


    //        @RequestMapping(value = "/build/find/{offset}/{pageSize}", method = RequestMethod.GET)
//    public void getAllSetsMapped(@RequestBody Criteria cr, @PathVariable int offset, @PathVariable int pageSize) {
//        setService.getPercentageOfSets(cr, offset, pageSize);
////        return ResponseEntity.status(HttpStatus.OK).body(myPartsToSet);
//    }
//    @RequestMapping(value = "/myparts", method = RequestMethod.GET)
//    public ResponseEntity<ApiResponse<PartDTO>> getMyParts(@RequestBody Criteria cr) {
//        ApiResponse<PartDTO> myParts = setService.getMyParts(cr);
//        return ResponseEntity.status(HttpStatus.OK).body(myParts);
//    }

    @RequestMapping(value = "/sets/{setNum}/parts", method = RequestMethod.GET)
    public ResponseEntity<Object> getSetParts(@PathVariable String setNum) {
        setService.getSetParts(setNum);
        Object response = apiService.getParts(setNum);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
