package ex08.office.step1;

// 사장은 일정만 잡으면 될까? 예약 전화까지 혼자 다 한다.
class Boss {
    void 일정잡기(String schedule) {
        System.out.println(schedule + " 일정을 접수합니다.");

        // 사장이 예약 전화까지 직접 건다. 역할이 너무 많다.
        System.out.println(schedule + " 예약 전화를 겁니다.");
        System.out.println(schedule + " 예약 확정.");
    }
}

public class App {
    public static void main(String[] args) {
        Boss boss = new Boss();

        boss.일정잡기("거래처 미팅");   // 접수도 예약도 사장 혼자.

        // 사장 혼자 다 한다. 예약 전화를 넘겨받을 비서가 필요하다.
    }
}
