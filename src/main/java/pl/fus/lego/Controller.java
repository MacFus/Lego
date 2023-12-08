package pl.fus.lego;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fus.lego.DTOs.MinifigDTO;
import pl.fus.lego.DTOs.SetDTO;
import pl.fus.lego.Entity.Minifigs;
import pl.fus.lego.Services.MinifigService;
import pl.fus.lego.Services.SetService;
import pl.fus.lego.UTILS.ApiResponse;
import pl.fus.lego.UTILS.Criteria;

@RestController
@RequestMapping("/app")
public class Controller {

    private final SetService setService;
    private final MinifigService minifigService;

    public Controller(SetService setService, MinifigService minifigService) {
        this.setService = setService;
        this.minifigService = minifigService;
    }

    @RequestMapping(value = "/sets/{offset}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<SetDTO>> getSetsWithCriteria (@RequestBody Criteria criteria, @PathVariable int offset, @PathVariable int pageSize){
        PageRequest pr = PageRequest.of(offset, pageSize).withSort(Sort.by(criteria.getSortField()));
        ApiResponse<SetDTO> setsWithPaginationAndCriteria = setService.findSetsWithPaginationAndCriteria(criteria, pr);
        return ResponseEntity.status(HttpStatus.OK).body(setsWithPaginationAndCriteria);
    }

    @RequestMapping(value = "/minifigs/{offset}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<MinifigDTO>> getMinifigsWithCriteria (@RequestBody Criteria criteria, @PathVariable int offset, @PathVariable int pageSize){
        PageRequest pr = PageRequest.of(offset, pageSize).withSort(Sort.by(criteria.getSortField()));
        ApiResponse<MinifigDTO> minifigsWithPaginationAndCriteria = minifigService.findMinifigWithPaginationAndCriteria(criteria, pr);
        return ResponseEntity.status(HttpStatus.OK).body(minifigsWithPaginationAndCriteria);
    }
}
