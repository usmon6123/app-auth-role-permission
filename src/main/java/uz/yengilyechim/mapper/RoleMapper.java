package uz.yengilyechim.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.yengilyechim.entity.Role;
import uz.yengilyechim.payload.RoleDto;
import uz.yengilyechim.payload.RoleResDto;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id",ignore = true)
    Role dtoToRole(RoleDto roleDto);

    @Mapping(target = "id",ignore = true)
    void updateRoleOutThePermissions(@MappingTarget Role role, RoleDto roleDto);

    RoleResDto roleToRseDto(Role role);

}
