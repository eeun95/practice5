package main;

import config.AppCtx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.DuplicateMemberException;
import spring.MemberRegisterService;
import spring.RegisterRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static AnnotationConfigApplicationContext ctx = null;

    public static void main(String[] args) throws IOException {
        ctx = new AnnotationConfigApplicationContext(AppCtx.class);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("명령어를 입력하세요:");
            String command = reader.readLine();
            if (command.equalsIgnoreCase("exit")) {
                System.out.println("종료");
                break;
            }
            if (command.startsWith("new ")) {
                processNewCommand(command.split(" "));
            } else if (command.startsWith("change ")) {
                processChangeCommand(command.split(" "));
            } else if (command.startsWith("list ")) {
                processListCommand();
            } else if (command.startsWith("info ")) {
                processInfoCommand(command.split(" "));
            } else {
                printHelp();
            }
        }
        ctx.close();
    }

    private static void processNewCommand(String[] arg) {
        if (arg.length != 5) {
            printHelp();
            return;
        }
        MemberRegisterService regSvc = ctx.getBean(MemberRegisterService.class);
        RegisterRequest req = new RegisterRequest();
        req.setEmail(arg[1]);
        req.setName(arg[2]);
        req.setPassword(arg[3]);
        req.setConfirmPassword(arg[4]);

        if (!req.isPasswordEqualToConfirmPassword()) {
            System.out.println("암호와 확인이 일치하지 않습니다.");
            return;
        }
        try {
            regSvc.regist(req);
            System.out.println("등록 완료");
        } catch (DuplicateMemberException e) {
            System.out.println("이미 존재하는 이메일입니다.");
        }
    }
    private static void processChangeCommand(String[] arg) {

    }
    private static void processListCommand() {

    }
    private static void processInfoCommand(String[] arg) {

    }
    private static void printHelp() {

    }
}
