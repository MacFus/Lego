package pl.fus.lego.Repositories;

import pl.fus.lego.Entity.InventorySets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventorySetsRepo extends JpaRepository<InventorySets, String> {
}
