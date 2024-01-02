package pl.fus.lego;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fus.lego.DTOs.MinifigDTO;
import pl.fus.lego.DTOs.SetDTO;
import pl.fus.lego.DTOs.ThemeDTO;
import pl.fus.lego.Entity.User;
import pl.fus.lego.Services.ApiService;
import pl.fus.lego.Services.Service;
import pl.fus.lego.UTILS.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/v1")
public class Controller {

    private final Service service;
    private final ApiService apiService;

    /**
     * The Desciption of the method to explain what the method does
     * @return String "Hello World"
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello() {
        return "Hello World";
    }

    /**
     * On login method retrieves user credentials and list of user sets
     * with all of its parts
     * @param userId - ID of user in db
     * @return User credentials & user sets with params
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserCredentials(@PathVariable Integer userId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getUserCredentials(userId));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping(value = "/mySets/{userId}", method = RequestMethod.GET)
    public ResponseEntity<ApiSetResponse> getUserSets(@PathVariable Integer userId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getMySetsWithParts(userId));
    }

    /**
     * On login method retrieves user credentials and list of user sets
     * with all of its parts
     * @return User credentials
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @RequestMapping(value = "/theme", method = RequestMethod.GET)
    public ResponseEntity<List<ThemeDTO>> getThemes() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getThemes());
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
        ApiResponse<SetDTO> sets = service.getSetsWithCriteria(new Criteria(sYear, eYear, min, max, theme, sortField, dir, search), offset, pageSize);
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
        ApiResponse<MinifigDTO> minifigs = service.getMinifigsWithCriteria(new Criteria(sYear, eYear, min, max, theme, sortField, dir, search), offset, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(minifigs);
    }

    @RequestMapping(value = "/sets/{setNum}", method = RequestMethod.GET)
    public ApiSetResponse getSetBySetNum(@PathVariable String setNum) {
        return service.getSetAndParts(setNum);
    }

    @RequestMapping(value = "/minifigs/{figNum}", method = RequestMethod.GET)
    public ApiMinifigResponse getMinifigByFigNum(@PathVariable String figNum) {
        return service.getMinifigAndParts(figNum);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addSetToUser(@RequestBody CrudSetRequest request) {
        try{
            return ResponseEntity.ok(service.addSetToUser(request));
        }catch (Exception ex){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSetFromUser(@RequestBody CrudSetRequest request) {
        try{
            return ResponseEntity.ok(service.deleteSetFromUser(request));
        }catch (Exception ex){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
        ApiResponse<SetDTO> setsWithMatch = (ApiResponse<SetDTO>) service.getBuildSetsWithCriteria(new Criteria(sYear, eYear, min, max, theme, sortField, dir, search, user, own), offset, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(setsWithMatch);
    }

}
