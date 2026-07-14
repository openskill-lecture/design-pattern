package ex15.elevator.step4;

import java.util.List;

// [step4] Factory(ex13/14 복습) — 엘베 종류가 여럿(일반/고속)이다.
// "무엇을 어떻게 만들까"를 팩토리 한 곳에 모은다. Building은 new를 모른다.

abstract class Elevator {
    protected String name;
    protected int currentFloor;
    protected int load;

    Elevator(String name, int currentFloor, int load) {
        this.name = name;
        this.currentFloor = currentFloor;
        this.load = load;
    }

    abstract void 이동한다(int floor);     // 종류마다 구현 (지금은 중복)

    String getName()      { return name; }
    int getCurrentFloor() { return currentFloor; }
    int getLoad()         { return load; }
}

class NormalElevator extends Elevator {
    NormalElevator(String name, int currentFloor, int load) { super(name, currentFloor, load); }

    void 이동한다(int floor) {
        int dist = Math.abs(currentFloor - floor);
        int time = dist * 2;               // 일반 : 층당 2초
        System.out.println("[" + name + "/일반] " + currentFloor + "층 → " + floor + "층 (" + time + "초)");
        currentFloor = floor;
        load++;
    }
}

class ExpressElevator extends Elevator {
    ExpressElevator(String name, int currentFloor, int load) { super(name, currentFloor, load); }

    void 이동한다(int floor) {
        int dist = Math.abs(currentFloor - floor);
        int time = dist * 1;               // 고속 : 층당 1초 (절반)
        System.out.println("[" + name + "/고속] " + currentFloor + "층 → " + floor + "층 (" + time + "초)");
        currentFloor = floor;
        load++;
    }
}

// 종류가 소수·고정이라 열거형으로 고정한다. (ex13 Driver 와 같은 결)
enum Kind { NORMAL, EXPRESS }

// Simple Factory : new를 대신 해준다. '무엇을 만들까' 결정을 이 한 곳에 모은다.
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
        ElevatorFactory factory = new ElevatorFactory();   // 생성은 팩토리에게. Building은 new를 모른다.

        Building building = new Building(List.of(
                factory.create(Kind.NORMAL,  "E1", 2, 5),
                factory.create(Kind.EXPRESS, "E2", 9, 0),
                factory.create(Kind.NORMAL,  "E3", 5, 2)));

        building.setStrategy(new NearestStrategy());
        building.외부호출(6);              // 가장 가까운 E3(일반) : 거리1 → 2초

        System.out.println("----------------------------");

        building.외부호출(10);             // 가장 가까운 E2(고속) : 거리1 → 1초 (같은 거리인데 절반)

        // NormalElevator·ExpressElevator의 이동한다()가 거의 똑같다(중복). 층당 시간만 다르다.
        // → step5 템플릿 메서드로 골격을 공통화한다.
    }
}
