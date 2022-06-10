package uz.yengilyechim.rolepermission.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.yengilyechim.rolepermission.entity.template.AbsUUIDEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Entity(name = "users")
public class User extends AbsUUIDEntity implements UserDetails {


    private String username; //unique bo'ladi telefon kiritsa ham bo'ladi

    private String password;

    @ManyToOne()
    private Role role;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(mappedBy = "user")
    private RolePermissionFromUser rolePermissionFromUser;


    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolePermissionFromUser.getPermissionEnum().stream().map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name()))
                .collect(Collectors.toSet());

//        role.getPermissions().stream().map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name()))
//                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public User(String username, String password, Role role, boolean enabled) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    public User(String username, String password, Role role, RolePermissionFromUser rolePermissionFromUser, boolean enabled) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.rolePermissionFromUser = rolePermissionFromUser;
        this.enabled = enabled;
    }
}
