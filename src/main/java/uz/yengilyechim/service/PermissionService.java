package uz.yengilyechim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yengilyechim.enums.PermissionEnum;
import uz.yengilyechim.payload.*;
import uz.yengilyechim.repository.RolePermissionFromUserRepository;
import uz.yengilyechim.repository.RoleRepository;
import uz.yengilyechim.repository.UserRepository;
import uz.yengilyechim.service.base.BaseService;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PermissionService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BaseService baseService;
    private final RolePermissionFromUserRepository rolePermissionFromUserRepository;


    public ApiResult<?> getAll() {

        PermissionEnum[] permissionEnums = PermissionEnum.values();

        Set<PermissionDto> permissionDtos = Arrays.stream(permissionEnums).map(PermissionDto::new).collect(Collectors.toSet());

        return ApiResult.successResponse(permissionDtos);

    }


}
