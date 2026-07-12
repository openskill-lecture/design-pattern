package ex06.exercise.ticket.start;

// [연습문제2 - 싱글톤] 번호표 발행기는 하나여야 번호가 안 겹친다.
//
// 은행 번호표 발행기(TicketMachine)는 부를 때마다 다음 번호를 뽑아준다.
// 그런데 지금은 아무나 new TicketMachine() 을 해서 발행기가 여러 대 생긴다.
// 발행기마다 번호가 1부터 따로 시작해서, 같은 번호(1번)가 중복 발행된다!
//
// ── 먼저 이대로 실행해 보라 ──────────────────
// 1번 창구와 2번 창구가 각자 발행기를 만들어, 손님 둘이 똑같이 1번표를 받는다.
//
// TODO 1) TicketMachine 을 싱글톤으로 고쳐라.
//         - 하나만 미리 만든다   : private static final TicketMachine instance = new TicketMachine();
//         - 생성자를 막는다      : private TicketMachine() {}
//         - 꺼내는 통로를 연다   : static TicketMachine getInstance() { return instance; }
//
// TODO 2) main 에서 new TicketMachine() 대신 TicketMachine.getInstance() 로 얻어라.
//         - 두 창구가 같은 발행기를 쓰면 번호가 1, 2, 3... 으로 유일하게 이어진다.

class TicketMachine {
    int last = 0;

    int 번호뽑기() {
        last++;
        return last;
    }
}

public class App {
    public static void main(String[] args) {
        // 창구마다 발행기를 따로 만든다. (여기가 문제)
        TicketMachine 창구1 = new TicketMachine();
        TicketMachine 창구2 = new TicketMachine();

        System.out.println("손님A 번호표 : " + 창구1.번호뽑기());  // 1
        System.out.println("손님B 번호표 : " + 창구2.번호뽑기());  // 1 ← 번호가 겹친다!

        System.out.println("----------------------------");

        System.out.println("같은 발행기인가 ? " + (창구1 == 창구2));  // false

        // 여기서부터 네 코드 : TicketMachine 을 싱글톤으로 고쳐, 번호가 안 겹치게 하라.
    }
}
