package uz.yengilyechim.rolepermission.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yengilyechim.rolepermission.aop.annotation.CheckPermission;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;
import uz.yengilyechim.rolepermission.payload.ApiResult;
import uz.yengilyechim.rolepermission.payload.RoleDto;
import uz.yengilyechim.rolepermission.repository.RoleRepository;
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

    @CheckPermission(values = "GET_ROLE")
    @GetMapping("/get-one/{id}")
    ApiResult<?> getOne(@PathVariable Long id) {
        return roleService.getOne(id);
    }

    @CheckPermission(values = "GET_ROLES")
    @GetMapping("/get-all")
    ApiResult<?> getAll() {
        return roleService.getAll();
    }

//    @CheckPermission(values = {"EDIT_ROLE","ALL"})
    @PreAuthorize(value = "hasAnyAuthority('EDIT_ROLE','ALL')")
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
