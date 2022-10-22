package uz.yengilyechim.enums;

public enum PayTypeEnum {
    CASH("Naqd"),
    TRANSFER("Pul o'tkazish"),
    CARD("Karta orqali"),
    DEBT("Qarz");

    private final String  nameUz;

    PayTypeEnum(String nameUz) {
        this.nameUz = nameUz;
    }

    public String getNameUz() {
        return nameUz;
    }
}
