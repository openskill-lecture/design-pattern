package ex15.elevator.step1;

// [step1] OOP 복습 — 엘베에게 "일을 시킨다". 층은 행위(내부호출)로만 바뀐다.
// 엘베가 한 대뿐이라 아직 "누가 갈까?" 하는 선택 문제는 없다.

class Elevator {
    private int currentFloor = 1;          // 상태는 감춘다. 밖에서 직접 못 바꾼다.

    // 내부 버튼 : 그 엘베가 직접 목표 층으로 이동한다.
    void 내부호출(int floor) {
        System.out.println(currentFloor + "층 → " + floor + "층 이동");
        currentFloor = floor;
    }

    int getCurrentFloor() { return currentFloor; }
}

public class App {
    public static void main(String[] args) {
        Elevator elevator = new Elevator();

        elevator.내부호출(5);              // 1층 → 5층 이동
        elevator.내부호출(9);              // 5층 → 9층 이동

        System.out.println("----------------------------");

        System.out.println("현재 층 : " + elevator.getCurrentFloor() + "층");
        // 필드를 안 까고 행위(내부호출)로만 층이 바뀐다. Tell, Don't Ask.
    }
}
