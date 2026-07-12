package ex08.compare.delegate;

// [델리게이트] 연예인이 자기 일 중 잡일을 매니저에게 넘긴다.
// 연예인 ──품는다(has-a)──▶ 매니저.  겉모습이 다르다(공연 ≠ 운전) = 대역이 아니라 분업.
class Manager {
    void 운전하기() { System.out.println("매니저 : 운전합니다."); }
    void 일정관리() { System.out.println("매니저 : 일정을 정리합니다."); }
}

class Star {
    private Manager manager = new Manager();   // 연예인이 매니저를 품는다(has-a)

    void 공연하기() {
        manager.운전하기();   // 잡일은 매니저에게 위임
        manager.일정관리();
        System.out.println("연예인 : 무대에서 공연합니다.");   // 자기 일은 자기가 한다
    }
}

public class App {
    public static void main(String[] args) {
        Star star = new Star();

        star.공연하기();

        System.out.println("----------------------------");

        // 팬은 '연예인'을 부른다. 연예인은 자기 일(공연)이 있고, 잡일(운전/일정)만 넘긴다 = 델리게이트(분업).
        // 겉모습이 달라(공연하기 ≠ 운전하기) 연예인 자리에 매니저를 꽂을 수 없다.
        //
        //   Manager m = new Star();   // ← 타입이 달라 불가. 매니저는 연예인의 대역이 아니다.
    }
}
