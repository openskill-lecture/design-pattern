package ex15.elevator.step6;

import java.util.ArrayList;
import java.util.List;

// [step6] Observer(ex09 복습) — 엘베는 '구독 명단'에게 도착만 통지한다.
// 표시판·대기 승객을 늘리든 빼든 엘베 코드는 그대로.

interface FloorObserver {                             // 통로(약속) : "도착하면 받는다"
    void 도착했다(String elevatorName, int floor);
}

class DisplayBoard implements FloorObserver {         // 층 표시판
    public void 도착했다(String elevatorName, int floor) {
        System.out.println("  [표시판] " + elevatorName + " → " + floor + "층 표시 갱신");
    }
}

class Passenger implements FloorObserver {            // 특정 층에서 기다리는 승객
    private String name;
    private int waitingFloor;

    Passenger(String name, int waitingFloor) {
        this.name = name;
        this.waitingFloor = waitingFloor;
    }

    public void 도착했다(String elevatorName, int floor) {
        if (floor == waitingFloor) {
            System.out.println("  [" + name + "] 내 층(" + floor + ") 도착! 탑승");
        }
    }
}

abstract class Elevator {
    protected String name;
    protected int currentFloor;
    protected int load;
    protected String kind;
    private List<FloorObserver> observers = new ArrayList<>();   // 구독 명단

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
        observers.forEach(o -> o.도착했다(name, floor));   // 도착을 명단 전체에 통지
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

class Building {
    private List<Elevator> elevators;
    private CallStrategy strategy;

    Building(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    void setStrategy(CallStrategy strategy) {
        this.strategy = strategy;
    }

    void 외부호출(int floor) {
        Elevator picked = strategy.고른다(elevators, floor);
        System.out.println(floor + "층 외부호출 → " + picked.getName() + " 배차");
        picked.이동한다(floor);
    }
}

public class App {
    public static void main(String[] args) {
        ElevatorFactory factory = new ElevatorFactory();

        Elevator e1 = factory.create(Kind.NORMAL,  "E1", 2, 5);
        Elevator e2 = factory.create(Kind.EXPRESS, "E2", 9, 0);
        Elevator e3 = factory.create(Kind.NORMAL,  "E3", 5, 2);

        Building building = new Building(List.of(e1, e2, e3));

        DisplayBoard board = new DisplayBoard();
        e1.구독(board);
        e2.구독(board);
        e3.구독(board);                     // 표시판은 모든 엘베를 구독
        e3.구독(new Passenger("승객A", 6));  // 6층에서 기다리는 승객

        building.setStrategy(new NearestStrategy());
        building.외부호출(6);              // E3가 6층 도착 → 표시판·승객A가 동시에 반응

        // 엘베는 도착만 통지한다. 관찰자를 늘리든 빼든 엘베 코드는 그대로.
    }
}
