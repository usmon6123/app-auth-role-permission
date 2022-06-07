package uz.yengilyechim.rolepermission.enums;

public enum PermissionEnum {

    ADD("add ","add qila olish huquqi"),
    UPDATE("edit ","edit qila olish huquqi"),
    DELETE("delete method","o'chirib tashash huquqi"),
    GET("get","ko'rish huquqi"),
    ALL("all Permissions","barcha yo'llarga kira olish huquqi");


    private String name;
    private String description;


    PermissionEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    PermissionEnum() {
    }
}
