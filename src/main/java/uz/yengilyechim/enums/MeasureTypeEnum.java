package uz.yengilyechim.enums;

public enum MeasureTypeEnum {
    KG("Kg"),
    COUNT("DONA");


    private final String nameUz;

    MeasureTypeEnum(String nameUz) {
        this.nameUz = nameUz;
    }

    public String getNameUz() {
        return nameUz;
    }
}
