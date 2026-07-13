package ex12.signup.step1;

// step1 : 가입 로직 안에 '진짜 저장(DB)'이 직접 박혀 있다.
// → 테스트 한 번 돌릴 때마다 진짜 회원이 쌓인다. 자동 테스트를 돌릴 수가 없다.

// 가입 서비스가 진짜 저장(DB 통신)을 직접 한다.
class SignupService {
    boolean 가입(String name) {
        if (name == null || name.isEmpty()) return false;

        // 진짜 DB 에 저장 (테스트할 때마다 실제로 회원이 쌓인다!)
        System.out.println("[진짜] DB 저장... " + name + " 회원 실제 저장됨");
        return true;
    }
}

public class App {
    public static void main(String[] args) {
        SignupService service = new SignupService();

        service.가입("alice");   // 테스트인데 진짜 회원이 쌓였다.
        System.out.println("→ 진짜 저장에 묶여 있어 자동 테스트도, 병렬 개발도 막혔다.");
    }
}
