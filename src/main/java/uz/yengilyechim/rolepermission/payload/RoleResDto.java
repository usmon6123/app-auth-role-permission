package uz.yengilyechim.rolepermission.payload;

import uz.yengilyechim.rolepermission.entity.Role;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

public class RoleResDto {

    private Long id;

    private String name;

    private String description;

    private Set<PermissionEnum> permissions;


    public RoleResDto(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.description = role.getDescription();
        this.permissions = role.getPermissions();
    }
}
