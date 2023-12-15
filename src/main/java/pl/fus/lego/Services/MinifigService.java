package pl.fus.lego.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.fus.lego.DTOs.MinifigDTO;
import pl.fus.lego.Entity.Minifigs;
import pl.fus.lego.Mappers.MinifigMapper;
import pl.fus.lego.Repositories.MinifigRepo;
import pl.fus.lego.UTILS.ApiResponse;
import pl.fus.lego.UTILS.Criteria;
import pl.fus.lego.UTILS.Utils;

import java.util.List;

@Service
public class MinifigService {
    private final MinifigRepo minifigRepo;
    private final MinifigMapper minifigMapper;

    public MinifigService(MinifigRepo minifigRepo, MinifigMapper minifigMapper) {
        this.minifigRepo = minifigRepo;
        this.minifigMapper = minifigMapper;
    }

    public ApiResponse<MinifigDTO> getMinifigsWithCriteria(Criteria cr, int offset, int pageSize){
        PageRequest pr = Utils.getPageRequest(cr, offset, pageSize);
        Page<Minifigs> minifigs;
        if(cr.getTheme() != null){
            minifigs = minifigRepo.findMinifigsByCriteria(cr, pr);
        }else{
            minifigs = minifigRepo.findMinifigsNoTheme(cr, pr);
        }
        List<MinifigDTO> minifigDTOList = minifigs.getContent().stream().map(minifigMapper::map).toList();
        System.out.println(minifigs);
        return new ApiResponse<>(minifigDTOList, minifigDTOList.size());
    }


}
