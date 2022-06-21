package uz.yengilyechim.rolepermission.component;


import org.springframework.http.HttpStatus;
import uz.yengilyechim.rolepermission.entity.Permission;
import uz.yengilyechim.rolepermission.entity.Role;
import uz.yengilyechim.rolepermission.entity.RolePermissionFromUser;
import uz.yengilyechim.rolepermission.entity.User;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;
import uz.yengilyechim.rolepermission.exception.RestException;
import uz.yengilyechim.rolepermission.repository.PermissionRepository;
import uz.yengilyechim.rolepermission.repository.RolePermissionFromUserRepository;
import uz.yengilyechim.rolepermission.repository.RoleRepository;
import uz.yengilyechim.rolepermission.repository.UserRepository;
import uz.yengilyechim.rolepermission.service.BaseService;
import uz.yengilyechim.rolepermission.utils.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
;

import java.util.*;
import java.util.stream.Collectors;

import static uz.yengilyechim.rolepermission.enums.PermissionEnum.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final RolePermissionFromUserRepository rolePermissionFromUserRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PermissionRepository permissionRepository;
    private final BaseService baseService;


    public static String ROLE_ADMIN;
    public static String ROLE_USER;

    @Value("${dataLoaderMode}")
    private String dataLoaderMode;

    private final static String userAdmin = "admin";


    @Override
    public void run(String... args) throws Exception {

        if (dataLoaderMode.equals("always")) {

            //BAZAGA BARCHA ENUMLARNI SAQLAYAPMIZ
            Set<Permission> permissions = Arrays.stream(values()).sequential().map(Permission::new).collect(Collectors.toSet());
            permissionRepository.saveAll(permissions);

            //ROLLAR YASAB OLIB SAQLAYABMIZ BAZAGA
            Role admin = new Role(AppConstant.ADMIN, "adminchamiz");
            Role user = new Role(AppConstant.USER, "userchiklar");
            roleRepository.save(admin);
            roleRepository.save(user);

            //IKKITA DEFAULT USER YASAB OLIB SAQLAYAPMIZ BAZAGA
            User adminUser = userRepository.save(new User(
                    userAdmin,
                    passwordEncoder.encode("admin123"),
                    admin,
                    true));

            User userDefault = userRepository.save(new User(
                    "user",
                    passwordEncoder.encode("user123"),
                    user,
                    true));


            //SAQLANGAN USERLARGA DEFAULT PERMISSIONLARNI BIRIKTIRIB BAZAGA SAQLAYAPMIZ
            rolePermissionFromUserRepository.save(
                    new RolePermissionFromUser(
                            adminUser.getId(),
                            admin.getId(),
                            permissions));

            //istalgan kirgan Userga beriladigan Huquqlar
            Permission permission = baseService.getPermission(GET);

            rolePermissionFromUserRepository.save(
                    new RolePermissionFromUser(
                            userDefault.getId(),
                            user.getId(),
                            new HashSet<>(Arrays.asList(permission))));

        }
        else {
            //ELSE ISHLAMAYDI!!!
            //QACHONKI PROEKT ISHLAYOTGAN PAYTDA YANGI YO'LLAR QO'SHILSA VA U YO'LLARGA YANGI PERMISSSIONLAR BILAN KIRILADIGAN BO'LSA ELSE ADMINGA BU PERMISSIONLARNI AVTAMAT QO'SHISHI KERAK EDI AMMO OPTIMALLASHTIRILMAY QOLDI
            //YANGI PERMISSIONLAR QO'SHILSA BAZAGA KIRIB TABLGA(ROLE_PERMISSION_FROM_USER_PERMISSIONS) QO'LDA ADMINGA BIRIKTIRIB QO'YING
//
//            User user = userRepository.findByUsername(userAdmin).orElseThrow(() -> new RestException("Sizda asosoiy Admin kiritilmagan", HttpStatus.NOT_FOUND));
//            Set<Permission> permissions = user.getRolePermissionFromUser().getPermissions();
//
//            //BAZADAGI ADMINNING PERMISSIONLARINI ICHIDA BO'LMAGAN PERMISSIONLAR
//            Set<Permission> newPermissions = Arrays.stream(values()).filter(
//                    permissionEnum -> permissions.stream().anyMatch(
//                            permission -> !permission.getNameEnum().equals(permissionEnum))).
//                    map(Permission::new).collect(Collectors.toSet());
//
//
////            List<Permission> savePermissions = permissionRepository.saveAll(newPermissions);
//            RolePermissionFromUser adminPermissionsFromUser = rolePermissionFromUserRepository.findByUserId(user.getId()).orElseThrow(() -> new RestException("Admin Permissions not found", HttpStatus.NOT_FOUND));
        }
    }
}
