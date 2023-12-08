package pl.fus.lego.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fus.lego.Entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
