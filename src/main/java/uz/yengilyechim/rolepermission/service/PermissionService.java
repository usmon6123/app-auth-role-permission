package uz.yengilyechim.rolepermission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.yengilyechim.rolepermission.entity.Permission;
import uz.yengilyechim.rolepermission.entity.Role;
import uz.yengilyechim.rolepermission.entity.RolePermissionFromUser;
import uz.yengilyechim.rolepermission.entity.User;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;
import uz.yengilyechim.rolepermission.exception.RestException;
import uz.yengilyechim.rolepermission.mapper.UserMapper;
import uz.yengilyechim.rolepermission.payload.*;
import uz.yengilyechim.rolepermission.repository.RolePermissionFromUserRepository;
import uz.yengilyechim.rolepermission.repository.RoleRepository;
import uz.yengilyechim.rolepermission.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PermissionService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final BaseService baseService;
    private final RolePermissionFromUserRepository rolePermissionFromUserRepository;


    public ApiResult<?> getAll() {

        PermissionEnum[] permissionEnums = PermissionEnum.values();

        Set<PermissionDto> permissionDtos = Arrays.stream(permissionEnums).map(PermissionDto::new).collect(Collectors.toSet());

        return ApiResult.successResponse(permissionDtos);

    }


}
