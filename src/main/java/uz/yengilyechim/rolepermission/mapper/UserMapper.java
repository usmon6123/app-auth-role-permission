package uz.yengilyechim.rolepermission.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.yengilyechim.rolepermission.entity.Role;
import uz.yengilyechim.rolepermission.entity.User;
import uz.yengilyechim.rolepermission.payload.RoleDto;
import uz.yengilyechim.rolepermission.payload.RoleResDto;
import uz.yengilyechim.rolepermission.payload.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToDto(User user);



}
