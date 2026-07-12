package ex01.oop.p1_state.step2;

// 상태는 행위(마신다, 먹는다)를 통해서만 변할까?
class Alice {
    public int height = 100;

    public void 물약을마신다() {
        height = height / 2; // 행위 → 상태 변화
        System.out.println("물약을 마셨다. 키 : " + height);
    }

    public void 케이크를먹는다() {
        height = height * 2;
        System.out.println("케이크를 먹었다. 키 : " + height);
    }
}

public class App {
    public static void main(String[] args) {
        Alice alice = new Alice();

        alice.물약을마신다(); // 100 → 50
        alice.케이크를먹는다(); // 50 → 100
        System.out.println("alice 키 : " + alice.height);

        System.out.println("----------------------------");

        alice.height = -500;
        System.out.println("alice 키 : " + alice.height); // 도데체 멀 했길래? 추적 불가능!
    }
}
