package uz.yengilyechim.rolepermission.repository;

import uz.yengilyechim.rolepermission.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String name);
    boolean existsByName(String name);

    void deleteByName(String name);
}
