package uz.yengilyechim.rolepermission.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;
@AllArgsConstructor@NoArgsConstructor@Data
public class RoleDto {


    private String name;

    private String description;

    private Set<PermissionEnum> permissions;


    public RoleDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
