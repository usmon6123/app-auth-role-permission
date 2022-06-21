package uz.yengilyechim.rolepermission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yengilyechim.rolepermission.entity.Permission;
import uz.yengilyechim.rolepermission.entity.Role;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByNameEnum(PermissionEnum nameEnum);

    @Query(value = "select *from  permission p where p.name_enum in(:permissions)", nativeQuery = true)
    List<Permission> getPermissionList(@Param("permissions") List<PermissionEnum> permissionEnums);

    Set<Permission> findByNameEnumIn(Set<PermissionEnum> nameEnum);

}
