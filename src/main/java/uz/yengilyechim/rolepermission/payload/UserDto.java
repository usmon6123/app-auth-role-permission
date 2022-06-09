package uz.yengilyechim.rolepermission.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yengilyechim.rolepermission.entity.Role;

import java.util.UUID;

@AllArgsConstructor@NoArgsConstructor@Data
public class UserDto {

    private String username;

    private RoleResDto roleResDto;


    //userni fieldlari proektga qarab har hil bo'ladi
}
