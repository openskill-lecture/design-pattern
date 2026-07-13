package ex08.office.step1;

// 사장은 일정만 잡으면 될까? 접수·전화·확정까지 혼자 다 한다.
class Boss {
    private int 다음번호 = 101;   // 예약번호 발급용 상태

    void 일정잡기(String schedule) {
        일정접수(schedule);
        int 번호 = 예약전화(schedule);      // 사장이 직접 전화까지
        예약확정(schedule, 번호);
    }

    void 일정접수(String schedule) {
        System.out.println(schedule + " 일정을 접수합니다.");
    }

    // 사장이 예약 전화까지 직접 건다. 역할이 너무 많다.
    int 예약전화(String schedule) {
        System.out.println(schedule + " 예약 전화를 겁니다.");
        return 다음번호++;
    }

    void 예약확정(String schedule, int 번호) {
        System.out.println(schedule + " 예약 확정 (예약번호 " + 번호 + ")");
    }
}

public class App {
    public static void main(String[] args) {
        Boss boss = new Boss();

        boss.일정잡기("거래처 미팅");   // 예약번호 101
        boss.일정잡기("채용 면접");     // 예약번호 102

        // 사장이 접수·전화·확정을 전부 혼자 한다. 전화·확정을 넘길 비서가 필요하다. (step2)
    }
}
