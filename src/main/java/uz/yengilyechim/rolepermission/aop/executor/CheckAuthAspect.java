package uz.yengilyechim.rolepermission.aop.executor;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.yengilyechim.rolepermission.aop.annotation.CheckPermission;
import uz.yengilyechim.rolepermission.entity.User;
import uz.yengilyechim.rolepermission.exception.RestException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Aspect
public class CheckAuthAspect {


    @Before(value = "@annotation(checkPermission)")//PASTDAGI CLASSNING CHECK PERMISSION ANNOTADSIYASIDAN OLDIN ISHLASHI KERAGLIGINI BUYURYAPDI
    public void checkAuthExecutor(CheckPermission checkPermission) {

        //TOKEN ORQALI KIRIB KELGAN USERNI OLYAPMIZ
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //ANNOTATSIYADAN KIRIB KELGAN PERMISSIONLARNI USERNING PERMISSIONLARI ORASIDAN IZLAB BOR BO'LGANLARINI YIG'YAPDI
        List<String> permissionList = Arrays.stream(checkPermission.values()).filter(s ->
                user.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(s))
        ).collect(Collectors.toList());

        if (permissionList.isEmpty())
            throw new RestException("userning bu yo'lga kirishga huquq(permissioni)i yo'q ",HttpStatus.FORBIDDEN);




//        //KIRIB KELGAN USERNING PERMISSIONLARINI ICHIDA ANNOTATSIYADAGI PERMISSION BORLIGINI TEKSHIRYAPDI TOPOLMASA THROW
//        boolean exist = user.getAuthorities().stream().anyMatch(grantedAuthority ->
//                grantedAuthority.getAuthority().equals(checkPermission.value()));

//        if (!exist)throw new RestException("Bunday huquq mavjudmas", HttpStatus.FORBIDDEN);

    }


}
