package pl.fus.lego.Services;

import pl.fus.lego.Entity.InventorySets;
import pl.fus.lego.Repositories.ColorsRepo;
import pl.fus.lego.Repositories.InventorySetsRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventorySetsService {
    private final InventorySetsRepo inventorySetsRepo;
    private final ColorsRepo colorsRepo;


    public InventorySetsService(InventorySetsRepo inventorySetsRepo, ColorsRepo colorsRepo) {
        this.inventorySetsRepo = inventorySetsRepo;
        this.colorsRepo = colorsRepo;
        System.out.println("dupa");
    }

    public List<InventorySets> getAllInventorySets(){
//        return List.of(new InventorySets());
        return inventorySetsRepo.findAll();
    }
}
