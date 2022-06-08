package uz.yengilyechim.rolepermission.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.yengilyechim.rolepermission.entity.Role;
import uz.yengilyechim.rolepermission.payload.RoleDto;
import uz.yengilyechim.rolepermission.payload.RoleResDto;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id",ignore = true)
    Role dtoToRole(RoleDto roleDto);

    @Mapping(target = "id",ignore = true)
    void updateRoleOutThePermissions(@MappingTarget Role role, RoleDto roleDto);

    RoleResDto roleToRseDto(Role role);

}
