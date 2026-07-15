package ex09.mediator;

import java.util.Scanner;

// 중재자: 기기는 서로를 모른다. Mediator에게 "나 켜졌어"만 통보한다.
// "누가 켜지면 누구를 끈다" 조율 규칙은 SmartHome 한 곳에 모인다.
// 청소기를 새로 붙여도 기존 기기 3개는 한 줄도 안 바뀐다 — 중재자에만 규칙 하나 추가.

interface Mediator {
    void 에어컨켜짐();
    void 보일러켜짐();
    void 창문열림();
    void 청소기켜짐();
}

class Window {
    private final Mediator mediator;
    private boolean closed = true;

    Window(Mediator mediator) {
        this.mediator = mediator;
    }

    void 연다() {
        closed = false;
        System.out.println("창문을 엽니다.");
        mediator.창문열림();       // 다른 기기는 모른다. 중재자에게만 통보
    }

    void 닫는다() {
        closed = true;
        System.out.println("창문을 닫습니다.");
    }

    boolean isOpen() {
        return !closed;
    }
}

class CoolAircon {
    private final Mediator mediator;
    private boolean running;

    CoolAircon(Mediator mediator) {
        this.mediator = mediator;
    }

    void 켠다() {
        running = true;
        System.out.println("에어컨을 켭니다.");
        mediator.에어컨켜짐();     // 다른 기기는 모른다. 중재자에게만 통보
    }

    void 끈다() {
        running = false;
        System.out.println("에어컨을 끕니다.");
    }

    boolean isRunning() {
        return running;
    }
}

class HeatBoiler {
    private final Mediator mediator;
    private boolean running;

    HeatBoiler(Mediator mediator) {
        this.mediator = mediator;
    }

    void 켠다() {
        running = true;
        System.out.println("보일러를 켭니다.");
        mediator.보일러켜짐();     // 다른 기기는 모른다. 중재자에게만 통보
    }

    void 끈다() {
        running = false;
        System.out.println("보일러를 끕니다.");
    }

    boolean isRunning() {
        return running;
    }
}

class RobotCleaner {
    private final Mediator mediator;
    private boolean running;

    RobotCleaner(Mediator mediator) {
        this.mediator = mediator;
    }

    void 켠다() {
        running = true;
        System.out.println("청소기를 켭니다.");
        mediator.청소기켜짐();     // 창문/에어컨/보일러는 모른다. 중재자에게만 통보
    }

    void 끈다() {
        running = false;
        System.out.println("청소기를 끕니다.");
    }

    boolean isRunning() {
        return running;
    }
}

class SmartHome implements Mediator {
    public final Window window = new Window(this);
    public final CoolAircon aircon = new CoolAircon(this);
    public final HeatBoiler boiler = new HeatBoiler(this);
    public final RobotCleaner cleaner = new RobotCleaner(this);

    // 조율 규칙이 여기 한 곳에 모인다 (기기 코드엔 규칙이 없다)
    public void 에어컨켜짐() {
        boiler.끈다();
        window.닫는다();
    }

    public void 보일러켜짐() {
        aircon.끈다();
        window.닫는다();
    }

    public void 창문열림() {
        aircon.끈다();
        boiler.끈다();
    }

    public void 청소기켜짐() {
        window.연다();   // 청소기는 먼지 → 환기하려 창문을 연다.
                         // 창문이 열리면 창문열림() 규칙이 에어컨·보일러를 끈다(그로 인해).
    }

    // 지금 이 순간 네 기기의 전체 상태를 한눈에 보여준다.
    void 상태출력() {
        System.out.println("   [ 상태 ] "
                + "창문:" + (window.isOpen() ? "열림" : "닫힘") + "  "
                + "에어컨:" + (aircon.isRunning() ? "켜짐" : "꺼짐") + "  "
                + "보일러:" + (boiler.isRunning() ? "켜짐" : "꺼짐") + "  "
                + "청소기:" + (cleaner.isRunning() ? "켜짐" : "꺼짐"));
    }
}

public class App {
    public static void main(String[] args) {
        SmartHome home = new SmartHome();
        Scanner scanner = new Scanner(System.in);

        home.상태출력();   // 시작 상태
        while (true) {
            System.out.println("----------------------------");
            System.out.println("[1] 창문 열기    [2] 창문 닫기");
            System.out.println("[3] 에어컨 켜기  [4] 에어컨 끄기");
            System.out.println("[5] 보일러 켜기  [6] 보일러 끄기");
            System.out.println("[7] 청소기 켜기  [8] 청소기 끄기");
            System.out.println("[0] 종료");
            System.out.print("입력> ");

            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            int cmd;
            try {
                cmd = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.");
                continue;
            }

            if (cmd == 0) {
                System.out.println("종료합니다.");
                break;
            }

            // 기기에게 "일을 시킬" 뿐, 연쇄 규칙은 중재자가 알아서 돌린다.
            switch (cmd) {
                case 1: home.window.연다();   break;
                case 2: home.window.닫는다();  break;
                case 3: home.aircon.켠다();   break;
                case 4: home.aircon.끈다();   break;
                case 5: home.boiler.켠다();   break;
                case 6: home.boiler.끈다();   break;
                case 7: home.cleaner.켠다();  break;
                case 8: home.cleaner.끈다();  break;
                default:
                    System.out.println("0~8 사이로 입력하세요.");
                    continue;
            }

            home.상태출력();   // 입력 후 전체 상태를 본다 (연쇄까지 반영된 결과)
        }

        scanner.close();
    }
}
