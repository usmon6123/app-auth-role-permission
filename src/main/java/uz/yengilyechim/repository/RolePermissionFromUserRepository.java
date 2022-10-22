package uz.yengilyechim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.yengilyechim.entity.RolePermissionFromUser;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RolePermissionFromUserRepository extends JpaRepository<RolePermissionFromUser, Long> {
//todo repo
    @Query(value = "select pr.name_enum from permission pr where id in(select r.permissions_id from role_permission_from_user_permissions  as r where (r.role_permission_from_user_id = (select id from role_permission_from_user as d  where  (d.user_id =:userId and d.role_id =:roleId))))", nativeQuery = true)
    Set<String> allPermissionByUserIdAndRoleId(UUID userId, Long roleId);

    @Modifying
    @Query(value = "delete  from role_permission_from_user where user_id =:userId ",nativeQuery = true)
    int deletedByUserId(@Param("userId") UUID userId);


    Optional<RolePermissionFromUser> findByUserId(UUID userId);

    //-----------------------------------------------------------------------------------------
    //BU TABLE role_permission_from_user_permission_enum TABLE BILAN FOREN_KEY QILIB BO'GLANGAN BOG'LANMANI ODDIY QILIB QO'YADI
    @Transactional
    @Query(value = "alter table role_permission_from_user_permission_enum drop constraint fkst3pbtnsxs9mla22trqrm2e1r", nativeQuery = true)
    @Modifying(clearAutomatically = true)
    void deleteOldForeignKey();

    //BU BUYRUQ role_permission_from_user_permission_enum BU TABLE FK_CASCADE_ON_DELETE QILIB BOG'LABERADI,
    @Query(value = "update table role_permission_from_user_permission_enum add constraint fk_cascade_on_delete foreign key (role_permission_from_user_id) references role_permission_from_user(id) on delete cascade", nativeQuery = true)
    @Modifying(clearAutomatically = true)
    void addNewForeignKeyOnDeleteCascade();
//-----------------------------------------------------------------------------------------
//    @Transactional
//    @Query(value = "update role_permission_from_user_permission_enum.tbl_policy set ac_status = 'INACTIVE' where pol_id = :policyId and version_no = :version_no and ac_status = 'ACTIVE'", nativeQuery = true)
//    @Modifying(clearAutomatically = true)
//    void deleteOldForeignKey(@Param("policyId") Long pol_id, @Param("version_no") Long version_no);

}
