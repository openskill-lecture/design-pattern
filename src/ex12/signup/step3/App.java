package ex12.signup.step3;

// step3 : step2 에서 저장을 분리해 갈아끼울 '이음새'가 생겼다.
// 이제 그 자리에 진짜(RealRepository) 대신 '가짜(Mock)'를 끼운다. → 진짜 회원 없이, 게다가 행위까지 검증.
//   이번 버그 = 중복 저장. 결과값(성공/true)만 보면 통과처럼 보인다 → count 로 잡는다.
//
// 왜 AI 프렌들리인가 :
//   - 계약(UserRepository) = AI 에게 '무엇을 만들지'를 모호함 없이 알려주는 스펙
//   - 자동 검증(main) = AI 가 짠 코드의 실수를 즉시 잡아낸다  ← 이번엔 '중복 저장'을 잡는다

interface UserRepository {
    void save(String name);
}

// 가짜(Mock) : 값은 없지만(void), '몇 번 불렸는지'를 기록한다.
// ★ 진짜(RealRepository)와 달리 진짜 회원이 안 쌓이고, 나아가 이 count(호출 기록)로 '행위'까지 검증한다.
class MockRepository implements UserRepository {
    int count = 0;                   // 호출 기록 (행위 검증용)

    public void save(String name) {
        count++;                     // 불릴 때마다 기록
    }
}

// 버그 : 가입 한 번에 저장을 '두 번' 호출한다. (저장 코드가 실수로 중복됐다)
class SignupService {
    private UserRepository repository;

    SignupService(UserRepository repository) {  // 주입
        this.repository = repository;
    }

    boolean 가입(String name) {
        if (name == null || name.isEmpty()) return false;
        repository.save(name);          // 첫 번째 저장
        repository.save(name);          // 버그! 두 번째 저장 (중복 저장)
        return true;
    }
}

public class App {
    public static void main(String[] args) {
        MockRepository mock = new MockRepository();
        SignupService service = new SignupService(mock);

        boolean ok = service.가입("alice");

        // 1) 결과값(상태)만 보면? → 성공(true). 값만 보면 중복 저장을 놓친다.
        System.out.println("가입 결과 : " + (ok ? "성공" : "실패") + "  ← 값만 보면 통과처럼 보인다");

        System.out.println("----------------------------");

        // 2) '몇 번 불렸나'(행위)를 보면? → 2번. 중복 저장을 잡는다. (이게 Mock)
        System.out.println("save 호출 횟수 : " + mock.count + " (예상 1)");
        System.out.println(mock.count == 1
                ? "[통과]"
                : "[실패] 중복 저장 적발! save가 " + mock.count + "번 불렸다.");
    }
}
