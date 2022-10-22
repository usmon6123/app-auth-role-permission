package uz.yengilyechim.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {
    @NotBlank(message = "username bo`sh bo`lmasligi kerak!")
    private String username;
    @NotBlank(message = "Parol bo`sh bo`lmasligi kerak!")
    private String password;
}
