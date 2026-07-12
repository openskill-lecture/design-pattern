package ex01.oop.p1_state.step3;


class Alice {
    private int height = 100; // 캡슐화 시킨다.

    public void 물약을마신다() {
        height = height / 2; // 행위 → 상태 변화
        System.out.println("물약을 마셨다. 키 : " + height);
    }

    public void 케이크를먹는다() {
        height = height * 2;
        System.out.println("케이크를 먹었다. 키 : " + height);
    }

    public int getHeight() {
        return height;
    }
}

public class App {
    public static void main(String[] args) {
        Alice alice = new Alice();

        // 좋음 : 행위를 통해서만 상태가 변한다.
        alice.물약을마신다();   // 100 → 50
        alice.케이크를먹는다(); // 50 → 100

        System.out.println("alice 키 : " + alice.getHeight());
    }
}
