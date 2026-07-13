package ex08.office.step2;

// 예약 전화·확정은 비서에게 넘긴다(위임). 사장은 예약 방법을 몰라도 된다.
class Secretary {
    private int 다음번호 = 101;   // 예약번호 발급용 상태

    int 예약전화(String schedule) {
        System.out.println(schedule + " 예약 전화를 겁니다.");
        return 다음번호++;
    }

    void 예약확정(String schedule, int 번호) {
        System.out.println(schedule + " 예약 확정 (예약번호 " + 번호 + ")");
    }
}

class Boss {
    private Secretary secretary = new Secretary();   // 일을 넘길 상대(delegate)

    void 일정잡기(String schedule) {
        일정접수(schedule);
        int 번호 = secretary.예약전화(schedule);   // 위임 — 비서가 예약번호를 발급해 돌려준다
        secretary.예약확정(schedule, 번호);        // 위임 — 사장이 받은 번호를 넘겨 확정
    }

    void 일정접수(String schedule) {
        System.out.println(schedule + " 일정을 접수합니다.");
    }
}

public class App {
    public static void main(String[] args) {
        Boss boss = new Boss();

        boss.일정잡기("거래처 미팅");   // 예약번호 101
        boss.일정잡기("채용 면접");     // 예약번호 102

        System.out.println("----------------------------");

        // 손님 → 사장(Boss/위임자) → 비서(Secretary/대리 수행자).
        // 사장은 접수만 하고, 전화·확정은 비서에게 위임한다.
        // 비서가 발급한 예약번호가 사장을 거쳐 흐른다.
    }
}
