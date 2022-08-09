package main;

import config.AppCtx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.converter.json.GsonBuilderUtils;
import spring.Member;
import spring.MemberDao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainForMemberDao {
    /*
        static 필드에 autowired 안되는 이유는
        스프링 컨테이너가 올라갈때 등록된 빈을 가지고 설정에 따른 의존성 주입을 하는데
        static이면 컨테이너가 올라가기도 전에 관련 의존성을 찾기 때문에
        memberDao 클래스가 빈으로 생성되기 전이므로 nullpointer가 발생
     */
    private static MemberDao memberDao;

    public static void main(String[] args) {
        // 자바 어노테이션을 이용한 클래스로부터 객체 설정정보를 가져옴
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);

        memberDao = ctx.getBean(MemberDao.class);

        selectAll();
        updateMember();
        insertMember();

        ctx.close();
    }

    private static void selectAll() {
        System.out.println("-----------------selectAll");
        int total = memberDao.count();
        System.out.println("전체 데이터 : " + total);
        List<Member> members = memberDao.selectAll();
        for (Member m : members) {
            System.out.println(m.getId() + ":" + m.getEmail() + ":" + m.getName());
        }
    }
    private static void updateMember() {
        System.out.println("-----------------updateMember");
        Member member = memberDao.selectByEmail("eeun95@gmail.com");
        String oldPw = member.getPassword();
        String newPw = Double.toHexString(Math.random());
        member.changePassword(oldPw, newPw);

        memberDao.update(member);
        System.out.println("암호 변경 : " + oldPw + ">" + newPw);
    }

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmss");

    private static void insertMember() {
        System.out.println("-----------------insertMember");

        String prefix = formatter.format(LocalDateTime.now());
        Member member = new Member(prefix + "@test.com", prefix, prefix, LocalDateTime.now());
        memberDao.insert(member);
        System.out.println(member.getId() + " 데이터 추가");
    }
}
