package ex15.elevator.step7;

import java.util.ArrayList;
import java.util.List;

// [step7] Singleton(ex06 복습) — 건물 관제센터는 딱 하나여야 한다.
// 생성자를 막고 getInstance()로만 얻는다. 어디서 불러도 같은 엘베 명단이다.

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

// 관제센터는 건물에 딱 하나. (ex06 Doorman 싱글턴과 같은 결)
class ControlCenter {
    private static final ControlCenter instance = new ControlCenter();

    private List<Elevator> elevators = new ArrayList<>();
    private CallStrategy strategy;

    private ControlCenter() {}                        // 생성자를 막는다. 밖에서 new 못한다.

    static ControlCenter getInstance() { return instance; }   // 유일한 통로

    void 등록(Elevator e)                { elevators.add(e); }
    void setStrategy(CallStrategy s)     { this.strategy = s; }

    void 외부호출(int floor) {
        Elevator picked = strategy.고른다(elevators, floor);
        System.out.println(floor + "층 외부호출 → " + picked.getName() + " 배차");
        picked.이동한다(floor);
    }
}

public class App {
    public static void main(String[] args) {
        ElevatorFactory factory = new ElevatorFactory();
        DisplayBoard board = new DisplayBoard();

        // 관제센터는 getInstance()로만 얻는다. 엘베를 등록한다.
        ControlCenter center = ControlCenter.getInstance();
        for (Elevator e : List.of(
                factory.create(Kind.NORMAL,  "E1", 2, 5),
                factory.create(Kind.EXPRESS, "E2", 9, 0),
                factory.create(Kind.NORMAL,  "E3", 5, 2))) {
            e.구독(board);
            center.등록(e);
        }

        center.setStrategy(new NearestStrategy());
        center.외부호출(6);                // 가장 가까운 E3 배차

        System.out.println("----------------------------");

        // 다른 곳에서 관제를 다시 불러도 같은 인스턴스 — 같은 엘베 명단이다.
        ControlCenter same = ControlCenter.getInstance();
        same.setStrategy(new IdlestStrategy());
        same.외부호출(6);                  // 같은 명단에 전략만 바꿔 E2(한가) 배차

        System.out.println("관제센터 하나? " + (center == same));   // true
    }
}
