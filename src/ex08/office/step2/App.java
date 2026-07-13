package ex08.office.step2;

// 예약 전화는 비서에게 넘긴다(위임). 사장은 예약 방법을 몰라도 된다.
class Secretary {
    void 예약전화(String schedule) {
        System.out.println(schedule + " 예약 전화를 겁니다.");
        System.out.println(schedule + " 예약 확정.");
    }
}

class Boss {
    private Secretary secretary = new Secretary();   // 일을 넘길 상대(delegate)를 들고 있다.

    void 일정잡기(String schedule) {
        System.out.println(schedule + " 일정을 접수합니다.");

        secretary.예약전화(schedule);          // 예약은 내가 안 한다. 비서에게 위임.
    }
}

public class App {
    public static void main(String[] args) {
        Boss boss = new Boss();

        boss.일정잡기("거래처 미팅");

        System.out.println("----------------------------");

        // 손님 → 사장(Boss) → 비서(Secretary)
        //          위임자        대리 수행자
        // 사장은 예약 방법을 몰라도, 예약 요청을 비서에게 전달만 한다. 이게 델리게이트.
    }
}
