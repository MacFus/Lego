package pl.fus.lego.Mappers;

import org.springframework.stereotype.Service;
import pl.fus.lego.DTOs.SetDTO;
import pl.fus.lego.Entity.Sets;

@Service
public class SetMapper {
    public SetDTO map(Sets set){
        SetDTO dto = new SetDTO();
        dto.setSetNum(set.getSetNum());
        dto.setName(set.getName());
        dto.setYear(Integer.parseInt(set.getYear()));
        dto.setThemeId(Integer.parseInt(set.getThemeId()));
        dto.setNumParts(set.getNumParts());
        dto.setImgUrl(set.getImgUrl());
        return dto;
    }
}
