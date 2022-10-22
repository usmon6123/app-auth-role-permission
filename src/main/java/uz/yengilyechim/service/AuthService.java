package uz.yengilyechim.service;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.yengilyechim.entity.Permission;
import uz.yengilyechim.entity.RolePermissionFromUser;
import uz.yengilyechim.entity.User;
import uz.yengilyechim.exception.RestException;
import uz.yengilyechim.payload.*;
import uz.yengilyechim.repository.RolePermissionFromUserRepository;
import uz.yengilyechim.repository.RoleRepository;
import uz.yengilyechim.repository.UserRepository;
import uz.yengilyechim.security.JwtProvider;
import uz.yengilyechim.service.base.BaseService;
import uz.yengilyechim.utils.AppConstant;

import java.util.Set;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;//tokenda kelgan userni bazada bor yo'qligini tekshiriberadi
    private final JwtProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final BaseService baseService;
    private final RolePermissionFromUserRepository rolePermissionFromUserRepository;

    public AuthService(@Lazy UserRepository userRepository, @Lazy AuthenticationManager authenticationManager, @Lazy JwtProvider jwtTokenProvider, @Lazy PasswordEncoder passwordEncoder, RoleRepository roleRepository, BaseService baseService, RolePermissionFromUserRepository rolePermissionFromUserRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.baseService = baseService;
        this.rolePermissionFromUserRepository = rolePermissionFromUserRepository;
    }


    public ApiResult<?> signIn(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken/*avtamatik loadUserByUsername() metitini ishlatib bazadan shu usernamelik user bormi deb teshkiryapti, bu user sistemaga kirmoqchi bo'lgan hol uchun */
                    (
                            loginDto.getUsername(),//Object principal
                            loginDto.getPassword() //Object credentials
                    ));
            User user = (User) authentication.getPrincipal();
            String refreshToken = jwtTokenProvider.generateTokenFromIdAndRoles(user.getId(), user.getRole(), false);
            String accessToken = jwtTokenProvider.generateTokenFromIdAndRoles(user.getId(), user.getRole(), true);
            return ApiResult.successResponse(new TokenDto(accessToken, refreshToken));
        } catch (BadCredentialsException e) {
            throw new RestException("Login yoki parol hato", HttpStatus.CONFLICT);
        }
    }

    public ApiResult<?> signUp(SignUpDto signUpDto) {
        if (userRepository.existsByUsername(signUpDto.getUsername()))
            throw new RestException("bu nomli username bazada mavjud", HttpStatus.CONFLICT);

        User user = new User(
                signUpDto.getUsername(),
                passwordEncoder.encode(signUpDto.getPassword()),
                roleRepository.findByName(AppConstant.USER).orElseThrow(() -> new RestException("role mavjudmas", HttpStatus.NOT_FOUND)),
                true
        );

        User saveUser = userRepository.save(user);

        //SIGN UP ORQALI KIRAYOTGAN USERGA GET HUQUQINI ULAB QOYYAPDI
        saveDefaultPermission(new DefaultPermissionDto(saveUser.getId(), saveUser.getRole().getId()));


        String accessToken = jwtTokenProvider.generateTokenFromIdAndRoles(user.getId(), user.getRole(), true);
        String refreshToken = jwtTokenProvider.generateTokenFromIdAndRoles(user.getId(), user.getRole(), false);
        return ApiResult.successResponse(new TokenDto(accessToken, refreshToken));

    }


    public ApiResult<TokenDto> refreshToken(TokenDto tokenDto) {
        try {
            jwtTokenProvider.validToken(tokenDto.getAccessToken().substring(7));

            return ApiResult.successResponse(tokenDto);
        } catch (ExpiredJwtException e) {
            try {
                //todo ss
                UUID userId = UUID.fromString(jwtTokenProvider.getClaimsFromToken(tokenDto.getRefreshToken().substring(7)).getSubject());
                User currentUser = userRepository.findById(userId).orElseThrow(() -> new RestException("USER_NOT_FOUND", HttpStatus.NOT_FOUND));

                return ApiResult.successResponse(new TokenDto(
                        jwtTokenProvider.generateTokenFromIdAndRoles(userId, currentUser.getRole(), true),
                        jwtTokenProvider.generateTokenFromIdAndRoles(userId, currentUser.getRole(), false)
                ));
            } catch (Exception ex) {
                throw new RestException("Refresh token buzligan", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            throw new RestException("Access token buzligan", HttpStatus.UNAUTHORIZED);
        }

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new RestException("USER NOT FOUND", HttpStatus.NOT_FOUND));
    }

    public UserDetails loadById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> RestException.restThrow("User not found", HttpStatus.NOT_FOUND));
    }

    public void saveDefaultPermission(DefaultPermissionDto defaultPermissionDto) {

        Set<Permission> permissions = baseService.getPermissions(defaultPermissionDto.getPermissionEnumList());
        rolePermissionFromUserRepository.save(
                new RolePermissionFromUser(
                        defaultPermissionDto.getUserId(),
                        defaultPermissionDto.getRoleId(),
                        permissions));
    }







}
