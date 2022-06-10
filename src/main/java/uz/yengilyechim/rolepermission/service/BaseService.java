package uz.yengilyechim.rolepermission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;
import uz.yengilyechim.rolepermission.mapper.RoleMapper;
import uz.yengilyechim.rolepermission.repository.RolePermissionFromUserRepository;
import uz.yengilyechim.rolepermission.repository.RoleRepository;
import uz.yengilyechim.rolepermission.repository.UserRepository;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BaseService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;
    private final RolePermissionFromUserRepository rolePermissionFromUserRepository;



    public Set<PermissionEnum> getPermissionsByUserIdAndRoleId(UUID userId, Long roleId){
        return  rolePermissionFromUserRepository.allPermissionByUserIdAndRoleId(userId, roleId);
    };

}
