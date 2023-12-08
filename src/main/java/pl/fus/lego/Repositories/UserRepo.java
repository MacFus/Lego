package pl.fus.lego.Repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.fus.lego.Entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}