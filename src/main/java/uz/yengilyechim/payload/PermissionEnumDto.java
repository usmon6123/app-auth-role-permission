package uz.yengilyechim.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionEnumDto {
private String nameEnum;
private String name;
private String description;

    public PermissionEnumDto(String nameEnum, String name) {
        this.nameEnum = nameEnum;
        this.name = name;
    }
}

