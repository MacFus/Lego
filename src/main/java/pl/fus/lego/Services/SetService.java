package pl.fus.lego.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.fus.lego.DTOs.SetDTO;
import pl.fus.lego.Entity.Sets;
import pl.fus.lego.Mappers.SetMapper;
import pl.fus.lego.Repositories.SetRepo;
import pl.fus.lego.UTILS.ApiResponse;
import pl.fus.lego.UTILS.Criteria;

import java.util.List;

@Service
public class SetService {
    private final SetRepo setRepo;
    private final SetMapper setMapper;

    public SetService(SetRepo setRepo, SetMapper setMapper) {
        this.setRepo = setRepo;
        this.setMapper = setMapper;
    }

    public ApiResponse<SetDTO> findSetsWithPaginationAndCriteria(Criteria criteria, PageRequest pr){
        System.out.println(criteria);
        Page<Sets> sets = setRepo.findSetsByCriteria(criteria, pr);
        List<SetDTO> setDTOList = sets.getContent().stream()
                .map(setMapper::map)
                .toList();
        return new ApiResponse<>(setDTOList.size(), setDTOList);
    }
}
