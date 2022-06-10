package uz.yengilyechim.rolepermission.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;
import uz.yengilyechim.rolepermission.entity.template.AbsLongEntity;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

//Bu enttityni ochishga ehtiyoj bir qancha user bitta roleni tanlaganida hammasiga
// bir xil huquq(permission) berilib qolayotgandi shuni hal qilish uchun ochildi
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Entity
public class RolePermissionFromUser extends AbsLongEntity {

    @JoinColumn(insertable = false, updatable = false)
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY, optional = false)
    private User user;

    @Column(name = "user_id")
    private UUID userId;

    @JoinColumn(insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Role role;

    @Column(name = "role_id")
    private Long roleId;



    @Enumerated(EnumType.STRING)
    @ElementCollection()
    private Set<PermissionEnum> permissionEnum;



    public RolePermissionFromUser(UUID userId, Long roleId, Set<PermissionEnum> permissionEnum) {
        this.userId = userId;
        this.roleId = roleId;
        this.permissionEnum = permissionEnum;
    }
}
