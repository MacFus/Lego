package pl.fus.lego.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.fus.lego.DTOs.MinifigDTO;
import pl.fus.lego.Entity.Minifigs;
import pl.fus.lego.Mappers.MinifigMapper;
import pl.fus.lego.Repositories.MinifigRepo;
import pl.fus.lego.UTILS.ApiResponse;
import pl.fus.lego.UTILS.Criteria;

import java.util.List;

@Service
public class MinifigService {
    private final MinifigRepo minifigRepo;
    private final MinifigMapper minifigMapper;

    public MinifigService(MinifigRepo minifigRepo, MinifigMapper minifigMapper) {
        this.minifigRepo = minifigRepo;
        this.minifigMapper = minifigMapper;
    }

    public ApiResponse<MinifigDTO> findMinifigWithPaginationAndCriteria(Criteria criteria, PageRequest pr){
        System.out.println(criteria);
        Page<Minifigs> minifigs;
        if(criteria.getTheme() != null){
            minifigs = minifigRepo.findMinifigsByCriteria(criteria, pr);
        }else{
            minifigs = minifigRepo.findMinifigsNoTheme(criteria, pr);
        }
        List<MinifigDTO> minifigDTOList = minifigs.getContent().stream().map(minifigMapper::map).toList();
        System.out.println(minifigs);
        return new ApiResponse<>(minifigDTOList.size(), minifigDTOList);
    }
}
