package uz.yengilyechim.rolepermission.component;


import uz.yengilyechim.rolepermission.entity.Role;
import uz.yengilyechim.rolepermission.entity.User;
import uz.yengilyechim.rolepermission.enums.PermissionEnum;
import uz.yengilyechim.rolepermission.repository.RoleRepository;
import uz.yengilyechim.rolepermission.repository.UserRepository;
import uz.yengilyechim.rolepermission.utils.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

import static uz.yengilyechim.rolepermission.enums.PermissionEnum.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Value("${dataLoaderMode}")
    private String dataLoaderMode;


    @Override
    public void run(String... args) throws Exception {
        if (dataLoaderMode.equals("always")) {
            Role admin = roleRepository.save(new Role(
                    AppConstant.ADMIN,
                    "bu admin",
                    new HashSet<>(Arrays.asList(PermissionEnum.values()))));

            Role user = roleRepository.save(new Role(
                    AppConstant.USER,
                    "bu ishchi",
                    new HashSet<>(Arrays.asList(GET))));

            userRepository.save(new User(
                    "admin",
                    passwordEncoder.encode("admin123"),
                    admin,
                    true));
            userRepository.save(new User(
                    "user",
                    passwordEncoder.encode("user123"),
                    user,
                    true));
        }
    }
}
