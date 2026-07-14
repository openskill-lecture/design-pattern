package ex15.elevator.step8;

import java.util.ArrayList;
import java.util.List;

// [step8] Facade(ex08 복습) = 최종 통합 — 제어판(ElevatorPanel)이 복잡한 서브시스템
// (팩토리·전략·관제·알림)을 버튼 뒤로 감춘다. 사용자는 버튼만 누른다.
// 내부호출은 자기 엘베만, 외부호출은 전략이 고른 엘베가 움직이고 도착 알림까지 뜬다.

interface FloorObserver {
    void 도착했다(String elevatorName, int floor);
}

class DisplayBoard implements FloorObserver {
    public void 도착했다(String elevatorName, int floor) {
        System.out.println("  [표시판] " + elevatorName + " → " + floor + "층 표시 갱신");
    }
}

abstract class Elevator {
    protected String name;
    protected int currentFloor;
    protected int load;
    protected String kind;
    private List<FloorObserver> observers = new ArrayList<>();

    Elevator(String name, int currentFloor, int load, String kind) {
        this.name = name;
        this.currentFloor = currentFloor;
        this.load = load;
        this.kind = kind;
    }

    void 구독(FloorObserver o) { observers.add(o); }

    final void 이동한다(int floor) {
        문닫는다();
        간다(floor);
        도착한다(floor);
        문연다();
    }

    protected void 문닫는다() { System.out.println("[" + name + "/" + kind + "] 문 닫힘"); }
    protected void 간다(int floor) {
        int dist = Math.abs(currentFloor - floor);
        int time = dist * 층당시간();
        System.out.println("  " + currentFloor + "층 → " + floor + "층 (" + time + "초)");
        currentFloor = floor;
        load++;
    }
    protected void 도착한다(int floor) {
        System.out.println("  " + floor + "층 도착");
        observers.forEach(o -> o.도착했다(name, floor));
    }
    protected void 문연다() { System.out.println("  문 열림"); }

    protected abstract int 층당시간();

    String getName()      { return name; }
    int getCurrentFloor() { return currentFloor; }
    int getLoad()         { return load; }
}

class NormalElevator extends Elevator {
    NormalElevator(String name, int currentFloor, int load) { super(name, currentFloor, load, "일반"); }
    protected int 층당시간() { return 2; }
}

class ExpressElevator extends Elevator {
    ExpressElevator(String name, int currentFloor, int load) { super(name, currentFloor, load, "고속"); }
    protected int 층당시간() { return 1; }
}

enum Kind { NORMAL, EXPRESS }

class ElevatorFactory {
    Elevator create(Kind kind, String name, int floor, int load) {
        if (kind == Kind.EXPRESS) return new ExpressElevator(name, floor, load);
        return new NormalElevator(name, floor, load);
    }
}

interface CallStrategy {
    Elevator 고른다(List<Elevator> elevators, int floor);
}

class NearestStrategy implements CallStrategy {
    public Elevator 고른다(List<Elevator> elevators, int floor) {
        Elevator picked = elevators.get(0);
        for (Elevator e : elevators) {
            if (Math.abs(e.getCurrentFloor() - floor) < Math.abs(picked.getCurrentFloor() - floor)) {
                picked = e;
            }
        }
        return picked;
    }
}

class IdlestStrategy implements CallStrategy {
    public Elevator 고른다(List<Elevator> elevators, int floor) {
        Elevator picked = elevators.get(0);
        for (Elevator e : elevators) {
            if (e.getLoad() < picked.getLoad()) {
                picked = e;
            }
        }
        return picked;
    }
}

class ControlCenter {
    private static final ControlCenter instance = new ControlCenter();

    private List<Elevator> elevators = new ArrayList<>();
    private CallStrategy strategy;

    private ControlCenter() {}

    static ControlCenter getInstance() { return instance; }

    void 등록(Elevator e)            { elevators.add(e); }
    void setStrategy(CallStrategy s) { this.strategy = s; }

    void 외부호출(int floor) {
        Elevator picked = strategy.고른다(elevators, floor);
        System.out.println(floor + "층 외부호출 → " + picked.getName() + " 배차");
        picked.이동한다(floor);
    }

    void 내부호출(String name, int floor) {           // 그 엘베만 자기 층으로 이동
        for (Elevator e : elevators) {
            if (e.getName().equals(name)) {
                System.out.println(name + " 내부호출 → " + floor + "층");
                e.이동한다(floor);
                return;
            }
        }
    }
}

// Facade — 복잡한 조립(팩토리 생성·관제 등록·표시판 구독·전략 설정)을 버튼 뒤로 감춘다.
class ElevatorPanel {
    private ControlCenter center = ControlCenter.getInstance();

    ElevatorPanel() {
        ElevatorFactory factory = new ElevatorFactory();
        DisplayBoard board = new DisplayBoard();
        for (Elevator e : List.of(
                factory.create(Kind.NORMAL,  "E1", 2, 5),
                factory.create(Kind.EXPRESS, "E2", 9, 0),
                factory.create(Kind.NORMAL,  "E3", 5, 2))) {
            e.구독(board);
            center.등록(e);
        }
        center.setStrategy(new NearestStrategy());
    }

    void 전략바꾼다(CallStrategy s)       { center.setStrategy(s); }
    void 외부버튼(int floor)              { center.외부호출(floor); }
    void 내부버튼(String name, int floor) { center.내부호출(name, floor); }
}

public class App {
    public static void main(String[] args) {
        ElevatorPanel panel = new ElevatorPanel();   // 복잡한 조립은 다 감춰졌다. 사용자는 버튼만.

        panel.외부버튼(6);                 // 외부호출 → 전략(가장 가까운)이 고른 E3 이동 + 도착 알림

        System.out.println("----------------------------");

        panel.전략바꾼다(new IdlestStrategy());
        panel.외부버튼(6);                 // 같은 버튼, 전략만 바꿔 E2(한가) 이동

        System.out.println("----------------------------");

        panel.내부버튼("E1", 12);          // 내부호출 → E1만 자기 층으로 이동

        // 버튼 하나 뒤에서 팩토리·전략·관제(하나)·템플릿 이동·도착 알림이 다 맞물려 돈다.
    }
}
