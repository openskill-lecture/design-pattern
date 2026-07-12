package ex12.exercise.mail.start;

// [연습 - Mock] 시작 : 회원가입 시 환영 메일을 보낸다.
// TODO: MailSender 를 계약(인터페이스)으로 뽑고,
//       '호출됐는지 기록하는' 가짜(Mock)를 만들어
//       "가입하면 환영 메일이 딱 1번 발송되는가?"를 검증해보자.
//       (Mock = 값이 아니라 '호출됐다는 사실'을 기록/검증하는 객체)
class MemberService {
    void 가입(String name) {
        System.out.println(name + " 가입 완료");
        // 진짜 메일 서버로 발송... (테스트할 때마다 진짜 메일이 나가면 곤란하다)
        System.out.println("환영 메일 발송(진짜)");
    }
}

public class App {
    public static void main(String[] args) {
        MemberService service = new MemberService();
        service.가입("홍길동");
        // 메일이 '정말 1번' 나갔는지 코드로 확인할 방법이 없다.
    }
}
