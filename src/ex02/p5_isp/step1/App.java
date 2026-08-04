package ex02.solid.p5_isp.step1;

// 직원이라면 제조·배달·청소를 다 할 수 있어야 한다? 홀알바는 커피를 못 만드는데...
interface Employee {
    void 제조한다();
    void 배달한다();
    void 청소한다();
}

class Barista implements Employee {
    public void 제조한다() { System.out.println("커피 제조"); }
    public void 배달한다() { System.out.println("배달"); }
    public void 청소한다() { System.out.println("청소"); }
}

// 홀알바는 청소만 한다. 그런데 인터페이스가 제조·배달까지 강제한다.
class HallStaff implements Employee {
    public void 제조한다() {
        // 비워야 함
    }
    public void 배달한다() {
        // 비워야 함
    }
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
