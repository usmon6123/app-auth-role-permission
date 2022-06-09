package uz.yengilyechim.rolepermission.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yengilyechim.rolepermission.entity.Role;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;


import java.util.List;
import java.util.Set;

@AllArgsConstructor@NoArgsConstructor@Data
public class UserRoleDto {

private Long roleId;

private Set<PermissionEnum> permissionEnumList;
}
