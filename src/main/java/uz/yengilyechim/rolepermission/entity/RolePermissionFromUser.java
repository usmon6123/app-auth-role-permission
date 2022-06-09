package uz.yengilyechim.rolepermission.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.yengilyechim.rolepermission.entity.template.AbsLongEntity;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
//Bu enttityni ochishga ehtiyoj bir qancha user bitta roleni tanlaganida hammasiga
// bir xil huquq(permission) berilib qolayotgandi shuni hal qilish uchun ochildi
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class RolePermissionFromUser extends AbsLongEntity {

    @JoinColumn(insertable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private User user;

    @Column(name = "user_id")
    private UUID userId;

    @JoinColumn(insertable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Role role;

    @Column(name = "role_id")
    private Long RoleId;

    @Enumerated(EnumType.STRING)
    private PermissionEnum permissionEnum;
}
