package pl.fus.lego.Controllers;

import pl.fus.lego.Entity.Colors;
import pl.fus.lego.Entity.InventorySets;
import pl.fus.lego.Repositories.ColorsRepo;
import pl.fus.lego.Services.InventorySetsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping(value = "/inventorySets")
public class InventorySetsController {
    private InventorySetsService inventorySetsService;
    private ColorsRepo colorsRepo;

    public InventorySetsController(InventorySetsService inventorySetsService, ColorsRepo colorsRepo) {
        this.inventorySetsService = inventorySetsService;
        this.colorsRepo = colorsRepo;
    }

    @GetMapping("/test")
    public List<String> getTest(){
        return List.of("test1");
    }

    @GetMapping("/all")
    List<InventorySets> getAllInventorySets() {
//        return colorsRepo.findAllById(Collections.singleton("1"));
        return inventorySetsService.getAllInventorySets();
    }

    @GetMapping("/colors")
    List<Colors> getAllColors() {
//        return colorsRepo.findAllById(Collections.singleton("1"));
        return colorsRepo.getAllColors();
    }
}
