package ex09.mediator;

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
}

public class App {
    public static void main(String[] args) {
        SmartHome home = new SmartHome();

        home.aircon.켠다();
        System.out.println("----------------------------");
        home.boiler.켠다();
        System.out.println("----------------------------");
        home.window.연다();
        System.out.println("----------------------------");
        home.aircon.켠다();     // 다시 에어컨을 켜고...
        home.cleaner.켠다();    // 청소기 → 창문 열림 → 방금 켠 에어컨·보일러가 꺼진다(연쇄)
    }
}
