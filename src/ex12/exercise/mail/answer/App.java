package ex12.exercise.mail.answer;

// [연습 정답 - Mock] 메일 발송을 계약으로 뽑고, '호출을 기록하는' 가짜(Mock)를 끼운다.
// ★ Mock 의 핵심 : 값을 대답하는 게 아니라(void), '몇 번 불렸는지'를 기록해 나중에 검증한다.
interface MailSender {
    void send(String to);
}

// 가짜(Mock) : 진짜로 보내지 않고, 호출된 횟수만 기록한다.
class MockMailSender implements MailSender {
    int count = 0;                   // 상태 : 몇 번 불렸나

    public void send(String to) {
        count++;                     // 값을 리턴하지 않는다. '불렸다'는 사실만 기록.
    }
}

class MemberService {
    private MailSender mail;

    MemberService(MailSender mail) {     // 주입
        this.mail = mail;
    }

    void 가입(String name) {
        System.out.println(name + " 가입 완료");
        mail.send(name);                 // 환영 메일 발송
    }
}

public class App {
    public static void main(String[] args) {
        MockMailSender mock = new MockMailSender();
        MemberService service = new MemberService(mock);

        service.가입("홍길동");

        System.out.println("----------------------------");
        // 검증 포인트 = '리턴값'이 아니라 '호출 횟수'(행위). 이게 Mock 의 쓰임이다.
        System.out.println("메일 발송 횟수 : " + mock.count);
        System.out.println(mock.count == 1
                ? "[통과] 환영 메일 딱 1번 발송"
                : "[실패] 메일이 " + mock.count + "번 발송됨");
    }
}
