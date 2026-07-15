package ex12.signup.step4;

// step4 : step3 은 "몇 번 불렀나"(count) 였다. 이번엔 "순서대로 불렀나"를 검증한다.
// 규칙 : 가입은 반드시 '중복검사 → 저장' 순서. 저장을 먼저 하면 이미 있는 회원을 덮어써버린다.
// → Mock 이 count 대신 '호출 순서'를 기록한다. (기록 대상은 검증 목적에 따라 달라진다)

interface UserStore {
    void 중복검사();
    void save(String name);
}

// 가짜(Mock) : 호출을 '순서대로' 기록한다. (step3 의 count 와는 기록 대상이 다르다)
class MockStore implements UserStore {
    String order = "";               // 무엇을, 어떤 순서로 불렀나

    public void 중복검사() {
        order += "중복검사 ";
    }

    public void save(String name) {
        order += "저장 ";
    }
}

// 버그 : 저장을 먼저 하고 중복검사를 나중에 한다. (순서가 뒤바뀜)
class SignupService {
    private UserStore store;

    SignupService(UserStore store) {      // 주입
        this.store = store;
    }

    void 가입(String name) {
        store.save(name);            // 버그! 저장을 먼저 해버린다
        store.중복검사();            // 중복검사가 뒤로 밀렸다
    }
}

public class App {
    public static void main(String[] args) {
        MockStore mock = new MockStore();
        SignupService service = new SignupService(mock);

        service.가입("alice");

        // 행위 검증 : '호출 순서'를 본다. count(몇 번) 로는 절대 못 잡는 버그다.
        System.out.println("호출 순서 : " + mock.order.trim());
        boolean ok = mock.order.trim().equals("중복검사 저장");
        System.out.println(ok
                ? "[통과] 중복검사 → 저장 순서 정상"
                : "[실패] 순서 뒤바뀜 적발! 이미 있는 회원을 덮어쓸 뻔했다. (기대: 중복검사 저장)");
    }
}
