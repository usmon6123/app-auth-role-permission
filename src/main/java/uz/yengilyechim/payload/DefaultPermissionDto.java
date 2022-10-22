package uz.yengilyechim.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yengilyechim.enums.PermissionEnum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static uz.yengilyechim.enums.PermissionEnum.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DefaultPermissionDto {

    private UUID userId;

    private Long roleId;

    private Set<PermissionEnum> permissionEnumList = new HashSet<>(Arrays.asList(
            VIEW,
            GET,
            GET_ROLE,
            GET_USER,
            DELETE_USER));

    public DefaultPermissionDto(UUID userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
