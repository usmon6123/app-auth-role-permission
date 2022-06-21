package uz.yengilyechim.rolepermission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yengilyechim.rolepermission.aop.annotation.CheckPermission;
import uz.yengilyechim.rolepermission.payload.ApiResult;
import uz.yengilyechim.rolepermission.service.PermissionService;
import uz.yengilyechim.rolepermission.utils.RestConstant;

@RestController
@RequiredArgsConstructor
@RequestMapping(RestConstant.PERMISSION_CONTROLLER)
public class PermissionController {
    private final PermissionService permissionService;

    @CheckPermission(values = {"GET_ROLES"})
    @GetMapping("/get-all")
    ApiResult<?>getAll(){
       return permissionService.getAll();
    }
}
