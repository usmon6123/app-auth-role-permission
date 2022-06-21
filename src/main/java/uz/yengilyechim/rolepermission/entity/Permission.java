package uz.yengilyechim.rolepermission.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.yengilyechim.rolepermission.entity.template.AbsLongEntity;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Permission extends AbsLongEntity {

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private PermissionEnum nameEnum;

//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @ManyToMany(mappedBy = "permissions")
//    private List<RolePermissionFromUser> rolePermissionFromUser;
//
//    public Permission(PermissionEnum nameEnum) {
//        this.nameEnum = nameEnum;
//    }
}
