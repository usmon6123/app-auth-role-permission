package uz.yengilyechim.rolepermission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.yengilyechim.rolepermission.entity.Role;
import uz.yengilyechim.rolepermission.exception.RestException;
import uz.yengilyechim.rolepermission.payload.ApiResult;
import uz.yengilyechim.rolepermission.payload.RoleDto;
import uz.yengilyechim.rolepermission.payload.RoleREsDto;
import uz.yengilyechim.rolepermission.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

private final RoleRepository roleRepository;


    public ApiResult<?> add(RoleDto roleDto) {

        boolean exists = roleRepository.existsByName(roleDto.getName());

        if (exists)throw new RestException("ROLE_ALREADY_EXISTS", HttpStatus.CONFLICT);

        Role role = new Role(roleDto.getName(), roleDto.getDescription(), roleDto.getPermissions());

        roleRepository.save(role);

        return ApiResult.successResponse("ROLE_ADDED");
    }

    public ApiResult<?> getAll() {

        List<Role> all = roleRepository.findAll();
        ArrayList<RoleREsDto> roles = null;
        for (Role role : all) {
            roles.add(new RoleREsDto(role));
        }
        return ApiResult.successResponse(roles);

    }
}
