package uz.yengilyechim.rolepermission.component;


import uz.yengilyechim.rolepermission.entity.Role;
import uz.yengilyechim.rolepermission.entity.RolePermissionFromUser;
import uz.yengilyechim.rolepermission.entity.User;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;
import uz.yengilyechim.rolepermission.repository.RolePermissionFromUserRepository;
import uz.yengilyechim.rolepermission.repository.RoleRepository;
import uz.yengilyechim.rolepermission.repository.UserRepository;
import uz.yengilyechim.rolepermission.utils.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
;

import java.util.Arrays;
import java.util.List;
import java.util.HashSet;

import static uz.yengilyechim.rolepermission.enums.PermissionEnum.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final RolePermissionFromUserRepository permissionFromUserRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public static String ROLE_ADMIN;
    public static String ROLE_USER;

    @Value("${dataLoaderMode}")
    private String dataLoaderMode;


    @Override
    public void run(String... args) throws Exception {
        if (dataLoaderMode.equals("always")) {
            Role admin = new Role(AppConstant.ADMIN, "adminchamiz");
            Role user = new Role(AppConstant.USER, "userchiklar");
            roleRepository.save(admin);
            roleRepository.save(user);
//
//            Role admin = roleRepository.save(new Role(
//                    AppConstant.ADMIN,
//                    "bu admin",
//                    new HashSet<>(Arrays.asList(PermissionEnum.values()))));
//            ROLE_ADMIN = admin.getName();
//
//            Role user = roleRepository.save(new Role(
//                    AppConstant.USER,
//                    "bu ishchi",
//                    new HashSet<>(Arrays.asList(GET))));
//            ROLE_USER = user.getName();

            User adminUser = userRepository.save(new User(
                    "admin",
                    passwordEncoder.encode("admin123"),
                    admin,
                    true));
            permissionFromUserRepository.save(
                    new RolePermissionFromUser(
                            adminUser.getId(),
                            admin.getId(),
                            new HashSet<>(Arrays.asList(PermissionEnum.values()))));
            User userDefault = userRepository.save(new User(
                    "user",
                    passwordEncoder.encode("user123"),
                    user,
                    true));
            permissionFromUserRepository.save(
                    new RolePermissionFromUser(
                            userDefault.getId(),
                            user.getId(),
                            new HashSet<>(Arrays.asList(GET))));

        }
    }
}
