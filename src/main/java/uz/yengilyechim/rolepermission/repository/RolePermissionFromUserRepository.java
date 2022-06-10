package uz.yengilyechim.rolepermission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yengilyechim.rolepermission.entity.Role;
import uz.yengilyechim.rolepermission.entity.RolePermissionFromUser;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RolePermissionFromUserRepository extends JpaRepository<RolePermissionFromUser,Long> {

    @Query(value = "select permission_enum from role_permission_from_user_permission_enum as p where p.role_permission_from_user_id = (\n" +
            "    select r.id from role_permission_from_user as r where (r.user_id=:userId and r.role_id =:roleId))",nativeQuery = true)
    Set<PermissionEnum> allPermissionByUserIdAndRoleId(UUID userId, Long roleId);

    void deleteByUserId(UUID userId);

}
