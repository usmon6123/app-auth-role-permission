package uz.yengilyechim.rolepermission.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.yengilyechim.rolepermission.aop.annotation.CheckPermission;
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

    @CheckPermission(values = "ADD")
    @PostMapping("/add")
    ApiResult<?> add(@RequestBody RoleDto roleDto) {
        return roleService.add(roleDto);
    }

    @CheckPermission(values = "GET")
    @PostMapping("/get-all")
    ApiResult<?> add() {
        return roleService.getAll();
    }



}
