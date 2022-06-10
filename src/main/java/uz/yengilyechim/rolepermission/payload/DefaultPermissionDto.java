package uz.yengilyechim.rolepermission.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static uz.yengilyechim.rolepermission.enums.PermissionEnum.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DefaultPermissionDto {

    private UUID userId;

    private Long roleId;

    private Set<PermissionEnum> permissionEnumList = new HashSet<>(Arrays.asList(GET));

    public DefaultPermissionDto(UUID userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
