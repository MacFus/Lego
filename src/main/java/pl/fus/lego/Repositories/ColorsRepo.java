package pl.fus.lego.Repositories;

import org.springframework.data.jpa.repository.Query;
import pl.fus.lego.Entity.Colors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColorsRepo extends JpaRepository<Colors, String> {
    @Query("SELECT u FROM Colors u ")
    List<Colors> getAllColors();
}
