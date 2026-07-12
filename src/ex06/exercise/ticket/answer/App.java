package ex06.exercise.ticket.answer;

// [연습문제2 정답 - 싱글톤] 발행기를 하나로 만들어 번호가 안 겹치게 한다.

class TicketMachine {
    // 하나만 미리 만든다.
    private static final TicketMachine instance = new TicketMachine();

    int last = 0;

    // 생성자를 막는다. 밖에서 new TicketMachine() 못한다.
    private TicketMachine() {}

    // 그 하나를 꺼내주는 유일한 통로.
    static TicketMachine getInstance() {
        return instance;
    }

    int 번호뽑기() {
        last++;
        return last;
    }
}

public class App {
    public static void main(String[] args) {
        // 두 창구가 같은 발행기를 얻는다.
        TicketMachine 창구1 = TicketMachine.getInstance();
        TicketMachine 창구2 = TicketMachine.getInstance();

        System.out.println("손님A 번호표 : " + 창구1.번호뽑기());  // 1
        System.out.println("손님B 번호표 : " + 창구2.번호뽑기());  // 2 ← 같은 발행기라 안 겹친다

        System.out.println("----------------------------");

        System.out.println("같은 발행기인가 ? " + (창구1 == 창구2));  // true
    }
}
