package pl.fus.lego.Mappers;

import org.springframework.stereotype.Service;
import pl.fus.lego.DTOs.InventoryPartsDTO;
import pl.fus.lego.Entity.InventoryParts;

@Service
public class InventoryPartsMapper {
    public InventoryPartsDTO map(Object obj){
        InventoryParts ip = (InventoryParts) obj;
        InventoryPartsDTO dto = new InventoryPartsDTO();
        dto.setQuantity(ip.getQuantity());
        dto.setPartNum(ip.getPartNum());
        dto.setImgUrl(ip.getImgUrl());
        dto.setColorId(ip.getColorId());
        return dto;
    }
}
