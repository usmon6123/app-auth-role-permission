package uz.yengilyechim.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpDto {

    @NotBlank(message = "usernameni kiritmadingiz!")
    @Size(min = 3, max = 100, message = "Ismingizni uzunroq kiriting(3-100)")
    private String username;

    @NotBlank(message = "passwordni kiritmadingiz!")
    @Size(min = 4, max = 100, message = "Ismingizni uzunroq kiriting(4-100)")
    private String password;



}
