package uz.yengilyechim.aop.annotation;

import java.lang.annotation.*;


//BU CLASS ANATATSIYA YASASH UCHUN QO'LLANILADI
@Documented
@Target(ElementType.METHOD)//YASAYOTGAN ANATADSIYAMIZNI QAYERDA ISHLATISHLIGIMIZNI KIRITAMIZ
@Retention(RetentionPolicy.RUNTIME)//QACHON VA QANDAY ISHLASHI KIRITILADI(bizga runtime holatida kerak bo'ladi bu anatatsiya)
public @interface CheckPermission {
//    String value();//value() ga Permission berib yuboriladi
    String[] values();//value() ga Permission yoki permissionlar EX: {"ADD","PUT","GET"} berib yuboriladi

    //    PermissionEnum[] permission() default {};}
}
