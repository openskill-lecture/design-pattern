package ex01.oop.p1_state.step1;

// 객체의 상태는 행위를 통해 변경한다
class Alice {
    int height = 100;
}


public class App {
    public static void main(String[] args) {
        // 나쁨 : 키가 왜 변했는지 아무도 모른다. 말도 안 되는 값도 들어간다.
        Alice alice = new Alice();
        
        alice.height = 50;
        System.out.println("alice" + alice.height);
    }
}
