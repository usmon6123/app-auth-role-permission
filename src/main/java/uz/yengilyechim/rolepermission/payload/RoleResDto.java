package uz.yengilyechim.rolepermission.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yengilyechim.rolepermission.entity.Role;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;
@AllArgsConstructor@NoArgsConstructor@Data@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleResDto {

    private Long id;

    private String name;

    private String description;

    private Set<PermissionEnum> permissions;

//todo role per
    public RoleResDto(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.description = role.getDescription();
    }
}
