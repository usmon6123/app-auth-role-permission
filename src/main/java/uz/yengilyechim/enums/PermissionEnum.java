package uz.yengilyechim.enums;

import lombok.Getter;

@Getter
public enum PermissionEnum {

    ADD("add ","add qila olish huquqi"),
    UPDATE("edit ","edit qila olish huquqi"),
    DELETE("delete method","o'chirib tashash huquqi"),
    GET("Ko'rish","istalgan bo'limni ko'rish huquqi"),
    ALL("all","barcha yo'llarga kira olish huquqi"),
    VIEW("tomosha qilamolish","istalgan bo'limni faqat tomosha qila olish huquqi"),
    GET_PERMISSION("Huquq(Permission)larni ko'rish","user o'zining Huquq(Permission)larini ko'ra olish huquqi"),

    ADD_ROLE("Yangi Lavozim(Role) qo'shish","Lavozim(Role)lar qo'shish huquqi M: Mingboshi,Yuzboshi, O'nboshi ..."),
    GET_ROLE("O'zining Lavozim(Role)ini ko'rish","Lavozimini ko'rish Huquqi"),
    GET_ROLES("Barcha Lavozim(Role)larni ko'rish",""),
    EDIT_ROLE("Lavozim(Role)ni o'zgartirish","Foydalanuvchining Lavozim(Role)ini o'zgartirish"),
    DELETE_ROLE("Lavozim(Role)ni o'chirish",""),

    GET_USER("Foydalanuvchi o'zi haqidagi malumotlarni ko'rishi",""),
    GET_USER_LIST("tanlangan Lavozim(Role)dagi barcha foydalanuvchilar ro'yhatini ko'rish",""),
    DELETE_USER("Foydalanuvchini o'chirish",""),
    EDIT_USER_ROLE("f",""),
    EDIT_USER("foydalanuvchi ozining accauntini sozlashi",""),

    ADD_DEBTOR("",""),
    ADD_DEBT_LIST("","");


    private String name;
    private String description;


    PermissionEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    PermissionEnum() {
    }


}
