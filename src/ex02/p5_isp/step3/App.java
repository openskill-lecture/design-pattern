package ex02.solid.p5_isp.step3;

// 직원이라면 제조·배달·청소를 다 할 수 있어야 한다? 홀알바는 커피를 못 만드는데...
interface Employee {
    void 제조한다();
    void 배달한다();
    void 청소한다();
}

abstract class HallAdapter implements Employee{
    public void 제조한다(){};
    public void 배달한다(){};
}

class HallStaff extends HallAdapter {
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
