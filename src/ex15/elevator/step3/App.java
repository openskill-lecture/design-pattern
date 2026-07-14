package ex15.elevator.step3;

import java.util.List;

// [step3] Strategy(ex03 복습) — "부르는 기준"을 통로(인터페이스)로 빼서 갈아끼운다.
// 같은 외부호출인데 전략만 바꾸면 다른 엘베가 온다.

class Elevator {
    private String name;
    private int currentFloor;
    private int load;

    Elevator(String name, int currentFloor, int load) {
        this.name = name;
        this.currentFloor = currentFloor;
        this.load = load;
    }

    void 이동한다(int floor) {
        System.out.println("[" + name + "] " + currentFloor + "층 → " + floor + "층 이동");
        currentFloor = floor;
        load++;
    }

    String getName()      { return name; }
    int getCurrentFloor() { return currentFloor; }
    int getLoad()         { return load; }
}

interface CallStrategy {                              // 통로(약속) : 후보 중 하나를 고른다
    Elevator 고른다(List<Elevator> elevators, int floor);
}

class NearestStrategy implements CallStrategy {       // 가장 가까운 엘베
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

class IdlestStrategy implements CallStrategy {        // 가장 한가한(부하 적은) 엘베
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

// [Context] 전략(CallStrategy)을 필드로 보유하고 setter로 갈아끼운다.
class Building {
    private List<Elevator> elevators;
    private CallStrategy strategy;

    Building(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    void setStrategy(CallStrategy strategy) {         // 런타임에 갈아끼운다
        this.strategy = strategy;
    }

    void 외부호출(int floor) {
        Elevator picked = strategy.고른다(elevators, floor);   // 선택은 전략에게 위임
        System.out.println(floor + "층 외부호출 → " + picked.getName() + " 배차");
        picked.이동한다(floor);
    }
}

public class App {
    public static void main(String[] args) {
        Building building = new Building(List.of(
                new Elevator("E1", 2, 5),
                new Elevator("E2", 9, 0),
                new Elevator("E3", 5, 2)));

        building.setStrategy(new NearestStrategy());
        building.외부호출(6);              // 가장 가까운 → E3(거리1)

        System.out.println("----------------------------");

        // ★ 같은 building, 같은 외부호출(6) — 전략만 갈아끼운다
        building.setStrategy(new IdlestStrategy());
        building.외부호출(6);              // 가장 한가한 → E2(load 0)

        // Building.외부호출은 한 줄도 안 바뀌었다. 전략만 갈아끼우면 다른 엘베가 온다.
    }
}
