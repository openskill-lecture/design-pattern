package ex02.solid.p5_isp.step4;

// 모든 구현 클래스가 꼭 직접 구현하지 않아도 되도록, 인터페이스가 기본 동작을 제공한다.
interface Employee {
    default void 제조한다(){};
    default void 배달한다(){};
    default void 청소한다(){};
}

// 바리스타는 제조·청소만 골라 구현한다.
class Barista implements Employee {
    public void 제조한다() { System.out.println("커피 제조"); }
    public void 청소한다() { System.out.println("청소"); }
}

// 홀알바는 청소만 구현한다. 억지로 떠안는 게 없다.
class HallStaff implements Employee {
    public void 청소한다() { System.out.println("청소"); }
}

public class App {
    public static void main(String[] args) {
        Employee 알바 = new HallStaff();
        알바.청소한다(); // 청소

        System.out.println("----------------------------");

        알바.제조한다(); // 할 줄 모르는 일을 강제당해 터진다!

        // 안 쓰는 기능까지 억지로 구현하게 만든다. ← ISP 위반
        // [실습] 큰 인터페이스를 능력별로 쪼개고, 필요한 것만 구현하게 바꿔보자 → step2
    }
}
