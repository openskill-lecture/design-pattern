package ex12.signup.step2;

// step2 : step1 은 가입이 저장까지 직접 했다 (책임이 안 나뉨). 저장을 계약(UserRepository)으로 '분리'한다.
// 가입은 '가입 흐름'만, 저장은 UserRepository 가 한다. 진짜 저장 구현(RealRepository)을 주입한다.
// → 출력(행동)은 step1 과 똑같이 '진짜 저장'이다. 바뀐 건 코드 '구조' — 갈아끼울 '이음새'가 생겼다.
//   (이 이음새에 가짜(Mock)를 끼워 테스트하는 건 step3 부터.)

interface UserRepository {
    void save(String name);
}

// 진짜 저장 : step1 에서 가입 안에 박혀 있던 DB 통신을 '제 자리'로 옮겼다.
class RealRepository implements UserRepository {
    public void save(String name) {
        System.out.println("[진짜] DB 저장... " + name + " 회원 실제 저장됨");
    }
}

// 가입은 저장을 직접 하지 않는다. 주입받은 UserRepository 에게 '시킨다'. (책임 분리)
class SignupService {
    private UserRepository repository;

    SignupService(UserRepository repository) {  // 주입
        this.repository = repository;
    }

    boolean 가입(String name) {
        if (name == null || name.isEmpty()) return false;
        repository.save(name);          // 저장은 UserRepository 담당
        return true;
    }
}

public class App {
    public static void main(String[] args) {
        // 진짜 저장 구현을 끼운다. 출력은 step1 과 같다(진짜 회원). 달라진 건 '구조'다.
        SignupService service = new SignupService(new RealRepository());

        service.가입("alice");   // [진짜] 저장됨 — 아직 진짜다.
        System.out.println("→ 저장을 분리했다. 이제 이 자리에 가짜를 끼우면 테스트가 된다. (step3)");
    }
}
