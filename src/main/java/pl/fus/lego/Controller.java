package pl.fus.lego;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fus.lego.DTOs.SetDTO;
import pl.fus.lego.Services.SetService;
import pl.fus.lego.UTILS.ApiResponse;
import pl.fus.lego.UTILS.Criteria;

import java.util.List;

@RestController
@RequestMapping("/app")
public class Controller {

    private final SetService setService;

    public Controller(SetService setService) {
        this.setService = setService;
    }

    @RequestMapping(value = "/sets/{offset}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<SetDTO>> getSetsWithCriteria (@RequestBody Criteria criteria, @PathVariable int offset, @PathVariable int pageSize){
        PageRequest pr = PageRequest.of(offset, pageSize);
//        ApiResponse<SetDTO> setsWithPaginationAndCriteria = setService.findSetsWithPaginationAndCriteria(criteria, pr);
        ApiResponse<SetDTO> setsWithPaginationAndCriteria = setService.findSetsWithPaginationAndCriteria(criteria, pr.withSort(Sort.by(criteria.getSortField())));
        return ResponseEntity.status(HttpStatus.OK).body(setsWithPaginationAndCriteria);
    }

    @RequestMapping(value = "/minifigs/{offset}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<SetDTO>> getMinifigsWithCriteria (@RequestBody Criteria criteria, @PathVariable int offset, @PathVariable int pageSize){
        PageRequest pr = PageRequest.of(offset, pageSize);
//        ApiResponse<SetDTO> setsWithPaginationAndCriteria = setService.findSetsWithPaginationAndCriteria(criteria, pr);
        ApiResponse<SetDTO> setsWithPaginationAndCriteria = setService.findSetsWithPaginationAndCriteria(criteria, pr.withSort(Sort.by(criteria.getSortField())));
        return ResponseEntity.status(HttpStatus.OK).body(setsWithPaginationAndCriteria);
    }
}
