package pl.szupke.onlineShop.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szupke.onlineShop.security.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
