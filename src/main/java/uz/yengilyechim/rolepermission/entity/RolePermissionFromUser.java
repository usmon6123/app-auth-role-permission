package uz.yengilyechim.rolepermission.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.yengilyechim.rolepermission.entity.template.AbsLongEntity;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

    @OnDelete(action = OnDeleteAction.CASCADE)
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

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany()
    @JoinColumn(name = "permissions")
    private Set<Permission> permissions;



    public RolePermissionFromUser(UUID userId, Long roleId, Set<Permission> permissions) {
        this.userId = userId;
        this.roleId = roleId;
        this.permissions = permissions;
    }



}
