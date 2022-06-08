package uz.yengilyechim.rolepermission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.yengilyechim.rolepermission.entity.Role;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;
import uz.yengilyechim.rolepermission.exception.RestException;
import uz.yengilyechim.rolepermission.mapper.RoleMapper;
import uz.yengilyechim.rolepermission.payload.ApiResult;
import uz.yengilyechim.rolepermission.payload.PermissionEnumDto;
import uz.yengilyechim.rolepermission.payload.RoleDto;
import uz.yengilyechim.rolepermission.payload.RoleResDto;
import uz.yengilyechim.rolepermission.repository.RoleRepository;
import uz.yengilyechim.rolepermission.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static uz.yengilyechim.rolepermission.component.DataLoader.ROLE_ADMIN;
import static uz.yengilyechim.rolepermission.component.DataLoader.ROLE_USER;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;


    public ApiResult<?> add(RoleDto roleDto) {

        boolean exists = roleRepository.existsByName(roleDto.getName());

        if (exists) throw new RestException("ROLE_ALREADY_EXISTS", HttpStatus.CONFLICT);

        Role role = new Role(roleDto.getName(), roleDto.getDescription(), roleDto.getPermissions());

        roleRepository.save(role);

        return ApiResult.successResponse("ROLE_ADDED");
    }

    public ApiResult<?> getOne(Long id) {

        Role role = roleRepository.findById(id).orElseThrow(() -> new RestException("ROLE_NOT_fOUND", HttpStatus.NOT_FOUND));

        RoleResDto roleResDto = roleMapper.roleToRseDto(role);

        return ApiResult.successResponse(roleResDto);


    }

    public ApiResult<?> getAll() {

        //BAZADAN BARCHA ROLE LARNI OLIBERADI
        List<Role> all = roleRepository.findAll();

        //ROLLARNI AYLANIB DTOGA AYLANTIRIB LISTGA YIG'YAPMIZ
        List<RoleResDto> roles = all.stream().map(RoleResDto::new).collect(Collectors.toList());

        return ApiResult.successResponse(roles);

    }

    public ApiResult<?> getAllPermission() {
        //BARCHA PERMISSIONLAR
        PermissionEnum[] values = PermissionEnum.values();

        List<PermissionEnumDto> permissionEnumDtoList = Arrays.stream(values).map(permissionEnum ->
                new PermissionEnumDto(
                        permissionEnum.name(),
                        permissionEnum.getName(),
                        permissionEnum.getDescription()
                )).collect(Collectors.toList());

        return ApiResult.successResponse(permissionEnumDtoList);
    }

    public ApiResult<?> delete(Long id) {

        //O'CHIRMOQCHI RO'LIMIZ BAZADA BO'LMASA THROWGA OTAMIZ
        Role role = roleRepository.findById(id).orElseThrow(() -> new RestException("THIS_ROLE_NOT_FOUND", HttpStatus.NOT_FOUND));

        //DATA_LOADERDA DEFAULT QO'SHILADIGAN ROLELARRNI OCHIRMOQCHI BO'LSA THROWGA OTAMIZ
        if (role.getName().equals(ROLE_ADMIN) && role.getName().equals(ROLE_USER))throw RestException.restThrow("DEFAULT ROLENI O'CHIRISH MUMKINMAS",HttpStatus.CONFLICT);


        //AGAR ROLEDAN FOYDALANADIGAN USERLAR BO'LSA THROWGA OTAMIZ
        if(userRepository.existsByRoleId(id)) throw RestException.restThrow("BU ROLEDAN USERLAR FOYDALANADI,FAQAT USERLAR FOYDALANMAYDIGAN ROLELARNI O'CHIRISH MUMKIN",HttpStatus.CONFLICT);

        roleRepository.deleteById(id);
        return ApiResult.successResponse("DELETED_ROLE");
    }

    public ApiResult<?> edit(Long id, RoleDto roleDto) {

        Role role = roleRepository.findById(id).orElseThrow(() -> new RestException("ROLE_NOT_fOUND", HttpStatus.NOT_FOUND));

        //DTO DAGI FIELDLARNI ROLEGA SET QILYAPDI
        roleMapper.updateRoleOutThePermissions(role,roleDto);

        roleRepository.save(role);

        return ApiResult.successResponse("ROLE_EDITED");

    }
}
