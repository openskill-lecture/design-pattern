package ex15.elevator.step2;

import java.util.List;

// [step2] 함정 — 엘베가 여러 대다. 외부호출은 그 중 하나를 골라야 한다.
// 그런데 "가장 가까운 것"이라는 선택 기준이 Building 안에 못박혀 있다.

class Elevator {
    private String name;
    private int currentFloor;
    private int load;                      // 처리한 요청 수 = 부하(바쁜 정도)

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

class Building {
    private List<Elevator> elevators;

    Building(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    // 외부(복도) 버튼 : 여러 대 중 하나를 골라 보낸다.
    // 선택 기준("가장 가까운")이 여기 못박혀 있다. ← 함정
    void 외부호출(int floor) {
        Elevator picked = elevators.get(0);
        int min = Math.abs(picked.getCurrentFloor() - floor);
        for (Elevator e : elevators) {
            int dist = Math.abs(e.getCurrentFloor() - floor);
            if (dist < min) {
                min = dist;
                picked = e;
            }
        }
        System.out.println(floor + "층 외부호출 → " + picked.getName() + " 배차 (거리 " + min + ")");
        picked.이동한다(floor);
    }
}

public class App {
    public static void main(String[] args) {
        // E1 : 2층·바쁨(load 5), E2 : 9층·한가(load 0), E3 : 5층(load 2)
        Building building = new Building(List.of(
                new Elevator("E1", 2, 5),
                new Elevator("E2", 9, 0),
                new Elevator("E3", 5, 2)));

        building.외부호출(6);              // 가장 가까운 E3(거리1) 배차

        System.out.println("----------------------------");

        // "가장 한가한 엘베로 바꿔줘"(E2, load 0) → Building.외부호출을 뜯어고쳐야 한다.
        // 선택 기준이 코드에 박제됐다. 전략을 갈아끼울 방법이 없다.
    }
}
