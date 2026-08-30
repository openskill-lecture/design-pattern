package ex02.solid.p4_lsp.step2;

// 모든 음료가 공통으로 할 수 있는 것만 둔다.
abstract class Drink {
    abstract void 제조한다();
}

// 리필 가능한 음료만 이 인터페이스를 구현한다.
interface Refillable {
    void 리필한다();
}

class Americano extends Drink implements Refillable {

    void 제조한다() {
        System.out.println("아메리카노 제조 완료");
    }

    public void 리필한다() {
        System.out.println("아메리카노 리필 완료");
    }
}

class Latte extends Drink {

    void 제조한다() {
        System.out.println("라떼 제조 완료");
    }
}

class BottledWater extends Drink {

    void 제조한다() {
        System.out.println("생수 제공 완료");
    }
}

public class App {
    public static void main(String[] args) {

        Drink[] drinks = {
            new Americano(),
            new Latte(),
            new BottledWater()
        };

        for (Drink drink : drinks) {
            drink.제조한다();   // 어떤 Drink가 와도 정상 동작
        }

        System.out.println("------------------");

        Refillable refillable = new Americano();
        refillable.리필한다();
    }
}