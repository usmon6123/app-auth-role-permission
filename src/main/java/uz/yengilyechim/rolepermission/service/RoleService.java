package uz.yengilyechim.rolepermission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.yengilyechim.rolepermission.entity.Role;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;
import uz.yengilyechim.rolepermission.exception.RestException;
import uz.yengilyechim.rolepermission.payload.ApiResult;
import uz.yengilyechim.rolepermission.payload.PermissionEnumDto;
import uz.yengilyechim.rolepermission.payload.RoleDto;
import uz.yengilyechim.rolepermission.payload.RoleResDto;
import uz.yengilyechim.rolepermission.repository.RoleRepository;
import uz.yengilyechim.rolepermission.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    public ApiResult<?> add(RoleDto roleDto) {

        boolean exists = roleRepository.existsByName(roleDto.getName());

        if (exists) throw new RestException("ROLE_ALREADY_EXISTS", HttpStatus.CONFLICT);

        Role role = new Role(roleDto.getName(), roleDto.getDescription(), roleDto.getPermissions());

        roleRepository.save(role);

        return ApiResult.successResponse("ROLE_ADDED");
    }

    public ApiResult<?> getOne(Long id) {

        Role role = roleRepository.findById(id).orElseThrow(() -> new RestException("ROLE_NOT_fOUND", HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(new RoleResDto(role));


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
        if (!roleRepository.existsById(id)) throw RestException.notFound("THIS_ROLE_NOT_FOUND");

        //AGAR ROLEDAN FOYDALANADIGAN USERLAR BO'LSA THROWGA OTAMIZ
        if(userRepository.existsByRoleId(id)) throw RestException.restThrow("BU ROLEDAN USERLAR FOYDALANADI,FAQAT USERLAR FOYDALANMAYDIGAN ROLELARNI O'CHIRISH MUMKIN",HttpStatus.CONFLICT);

        roleRepository.deleteById(id);
        return ApiResult.successResponse("DELETED_ROLE");
    }

    public ApiResult<?> edit(Long id, RoleDto roleDto) {
        //todo role edit
        Role role = roleRepository.findById(id).orElseThrow(() -> new RestException("ROLE_NOT_fOUND", HttpStatus.NOT_FOUND));

        return null;

    }
}
