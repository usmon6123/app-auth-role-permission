package uz.yengilyechim.rolepermission.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yengilyechim.rolepermission.aop.annotation.CheckPermission;
import uz.yengilyechim.rolepermission.entity.User;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;
import uz.yengilyechim.rolepermission.payload.ApiResult;
import uz.yengilyechim.rolepermission.payload.RoleDto;
import uz.yengilyechim.rolepermission.repository.RoleRepository;
import uz.yengilyechim.rolepermission.security.CurrentUser;
import uz.yengilyechim.rolepermission.service.RoleService;
import uz.yengilyechim.rolepermission.utils.RestConstant;

@Slf4j
@RestController
@RequestMapping(RestConstant.ROLE_CONTROLLER)
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @CheckPermission(values = "ADD_ROLE")
    @PostMapping("/add")
    ApiResult<?> add(@RequestBody RoleDto roleDto) {

        return roleService.add(roleDto);
    }

    //USERNING ROLE VA PERMISSIONLARINI OLADIGAN YO'LI
    @CheckPermission(values = {"GET","GET_ROLE"})
    @GetMapping("/get-one/current-user")
    ApiResult<?> getOne(@CurrentUser User user) {
        return roleService.getOne(user.getId(),user.getRole().getId());
    }

    //FAQAT BAZADAGI BARCHA ROLLARNI QAYTARADI
    @CheckPermission(values = "GET_ROLES")
    @GetMapping("/get-all")
    ApiResult<?> getAll() {
        return roleService.getAll();
    }

    //BU YO'LGA ASOSAN ADMIN KIRIB QO'L OSTIDAGILARNING RO'LE VA PERMISSIONLARINI O'ZGARTIRISHI MUMKIN
    @CheckPermission(values = {"EDIT_ROLE"})
    @PutMapping("/edit/{id}")
    ApiResult<?>edit(@PathVariable Long id,@RequestBody RoleDto roleDto){
        return roleService.edit(id,roleDto);
    }

    @CheckPermission(values = "DELETE_ROLE")
    @DeleteMapping("/delete/{id}")
    ApiResult<?>delete(@PathVariable Long id){

        return roleService.delete(id);
    }

    @CheckPermission(values = {"GET_PERMISSION"})
    @GetMapping("/get-all-permission")
    ApiResult<?>getAllPermission(){
        return roleService.getAllPermission();
    }



}
