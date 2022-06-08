package uz.yengilyechim.rolepermission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.yengilyechim.rolepermission.entity.Role;
import uz.yengilyechim.rolepermission.entity.User;
import uz.yengilyechim.rolepermission.exception.RestException;
import uz.yengilyechim.rolepermission.payload.ApiResult;
import uz.yengilyechim.rolepermission.payload.UserDtoByRole;
import uz.yengilyechim.rolepermission.payload.UserRoleDto;
import uz.yengilyechim.rolepermission.repository.RoleRepository;
import uz.yengilyechim.rolepermission.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ApiResult<?> getAllByRole(Long roleId) {

        //TEPADA KELGAN ROLEGA OLGAN USERLAR RO'YHATINI QAYTARADI
        List<User> userList = userRepository.findAllByRoleId(roleId);

        //RO'YHAT BO'SH BO'LSA BO'SH LIST QAYTARAMIZ
        if (userList.isEmpty()) return ApiResult.successResponse(new ArrayList<>());

        //USERLARNI USERNAME VA ID LARINI DTOGA O'RAB LISTGA YIG'IB QAYTARYAPMIZ
        List<UserDtoByRole> collect = userList.stream().map(UserDtoByRole::new).collect(Collectors.toList());
        return ApiResult.successResponse(collect);
    }

    public ApiResult<?> delete(UUID id) {
        //
        if (existsUserOrElseThrow(id))
            userRepository.deleteById(id);

        return ApiResult.successResponse("DELETED_USER");

    }

    public ApiResult<?> editUserRole(UUID id, UserRoleDto userRoleDto) {

        //ID ORQALI BAZADAN USER QAYTARADI AKS HOLDA THROW
        User user = getUserOrElseThrow(id);

        //ID ORQALI ROLENI OLIB KELYAPMIZ AKS HOLDA THROW
        Role role = roleRepository.findById(userRoleDto.getRoleId()).orElseThrow(() -> new RestException("ROLE_NOTE_FOUND", HttpStatus.NOT_FOUND));

        user.setRole(role);

        userRepository.save(user);

        return ApiResult.successResponse("SUCCESS_EDITED");
    }



//    ---------------------------helper method----------------------------

    //BU METHOD KIRIB KELGAN IDLI USER BAZADA BO'LSA TRUE AKS HOLDA THROWGA OTADI
    public boolean existsUserOrElseThrow(UUID userId) {

        //AGAR O'CHIRMOQCHI BO'LGAN USERIMIZ TOPILMASA THROW
        if (!userRepository.existsById(userId)) throw RestException.restThrow("USER_NOT_FOUND", HttpStatus.NOT_FOUND);
        return true;
    }

    //ID ORQALI BAZADAN USER QAYTARADI AKS HOLDA THROW
    public User getUserOrElseThrow(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RestException("USER_NOT_FOUND", HttpStatus.NOT_FOUND));
        return user;
    }
}
