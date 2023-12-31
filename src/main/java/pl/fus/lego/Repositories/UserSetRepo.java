package pl.fus.lego.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fus.lego.Entity.User;
import pl.fus.lego.Entity.UserSets;

import java.util.Optional;

public interface UserSetRepo extends JpaRepository<UserSets, Integer> {
    Optional<UserSets> findUserSetsBySetNumAndUserId(String setNum, Integer userId);
}
