package uz.yengilyechim.rolepermission.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum PermissionEnum {

    ADD("add ","add qila olish huquqi"),
    UPDATE("edit ","edit qila olish huquqi"),
    DELETE("delete method","o'chirib tashash huquqi"),
    GET("get","ko'rish huquqi"),
    ALL("all Permissions","barcha yo'llarga kira olish huquqi"),

    GET_PERMISSION("GET_PERMISSION","userga permissionlarni shu yo'ldan topasiz"),

    ADD_ROLE,
    GET_ROLE,
    GET_ROLES,
    EDIT_ROLE,
    DELETE_ROLE,

    GET_USER_LIST,
    DELETE_USER,

    S;

    private String name;
    private String description;


    PermissionEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    PermissionEnum() {
    }


}
