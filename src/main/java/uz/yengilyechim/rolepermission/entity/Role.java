package uz.yengilyechim.rolepermission.entity;

import uz.yengilyechim.rolepermission.entity.template.AbsLongEntity;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Role extends AbsLongEntity {

    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<PermissionEnum> permissions;


}
