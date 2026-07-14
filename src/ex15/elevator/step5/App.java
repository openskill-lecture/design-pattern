package ex15.elevator.step5;

import java.util.List;

// [step5] Template Method(ex07 복습) — 이동 사이클 골격을 부모가 잠근다(final).
// 문닫기 → 이동 → 도착 → 문열기 순서는 고정. 변하는 것(층당 시간)만 자식이 채운다.

abstract class Elevator {
    protected String name;
    protected int currentFloor;
    protected int load;
    protected String kind;                 // 표시용 (일반/고속)

    Elevator(String name, int currentFloor, int load, String kind) {
        this.name = name;
        this.currentFloor = currentFloor;
        this.load = load;
        this.kind = kind;
    }

    // final : 골격(순서)은 못 바꾼다. 이게 템플릿 메서드.
    final void 이동한다(int floor) {
        문닫는다();
        간다(floor);
        도착한다(floor);
        문연다();
    }

    // 고정 스텝
    protected void 문닫는다() { System.out.println("[" + name + "/" + kind + "] 문 닫힘"); }
    protected void 간다(int floor) {
        int dist = Math.abs(currentFloor - floor);
        int time = dist * 층당시간();      // ← 변하는 것만 자식에게 물어본다
        System.out.println("  " + currentFloor + "층 → " + floor + "층 (" + time + "초)");
        currentFloor = floor;
        load++;
    }
    protected void 도착한다(int floor) { System.out.println("  " + floor + "층 도착"); }
    protected void 문연다()          { System.out.println("  문 열림"); }

    // 변하는 스텝 : 자식이 반드시 채운다. (일반=2, 고속=1)
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

        Building building = new Building(List.of(
                factory.create(Kind.NORMAL,  "E1", 2, 5),
                factory.create(Kind.EXPRESS, "E2", 9, 0),
                factory.create(Kind.NORMAL,  "E3", 5, 2)));

        building.setStrategy(new NearestStrategy());
        building.외부호출(6);              // E3 일반 : 골격대로 문닫기→이동(2초)→도착→문열기

        System.out.println("----------------------------");

        building.외부호출(10);             // E2 고속 : 같은 골격, 층당시간()만 1초

        // 문닫기→이동→도착→문열기 골격은 부모가 한 번만 정하고 잠갔다(final).
        // 자식은 층당시간() 한 칸만 채운다. step4의 중복이 사라졌다.
    }
}
