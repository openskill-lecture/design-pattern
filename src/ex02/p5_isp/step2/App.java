package ex02.solid.p5_isp.step2;

// 능력별로 인터페이스를 쪼갠다. 필요한 것만 골라 구현한다.
interface Maker { void 제조한다(); }      // 제조 가능
interface Deliverer { void 배달한다(); }  // 배달 가능
interface Cleaner { void 청소한다(); }    // 청소 가능

// 바리스타는 제조·청소만 골라 구현한다.
class Barista implements Maker, Cleaner {
    public void 제조한다() { System.out.println("커피 제조"); }
    public void 청소한다() { System.out.println("청소"); }
}

// 홀알바는 청소만 구현한다. 억지로 떠안는 게 없다.
class HallStaff implements Cleaner {
    public void 청소한다() { System.out.println("청소"); }
}

public class App {
    public static void main(String[] args) {
        Barista barista = new Barista();
        barista.제조한다();
        barista.청소한다();

        System.out.println("----------------------------");

        HallStaff 알바 = new HallStaff();
        알바.청소한다(); // 자기가 할 수 있는 일만 가진다. 터질 일이 없다.

        // 안 쓰는 기능을 강요당하지 않는다. ← ISP
    }
}
