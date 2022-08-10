package main;

import config.AppCtx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ChangePasswordService;
import spring.MemberNotFoundException;
import spring.WrongIdPasswordException;

public class MainForCPS {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);

        ChangePasswordService cps = ctx.getBean("changePasswordService", ChangePasswordService.class);

        try {
            cps.changePassword("eeun95@gmail.com", "1234", "1111");
            System.out.println("암호 변경 완료");
        } catch (MemberNotFoundException e) {
            System.out.println("회원데이터 없음");
        } catch (WrongIdPasswordException e) {
            System.out.println("암호 불일치");
        }

        ctx.close();
    }
}
