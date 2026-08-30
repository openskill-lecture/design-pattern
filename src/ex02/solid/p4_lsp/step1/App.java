package ex02.solid.p4_lsp.step1;

// 모든 Drink는 제조할 수 있다고 약속했다. 정말 모든 자식이 지킬까?
abstract class Drink {
    abstract void 제조한다();
    abstract void 리필한다();
}

// 모든 Drink는 제조와 리필이 가능하다고 설계했다.
// 그런데 정말 모든 음료가 리필될 수 있을까?
class Americano extends Drink {

    void 제조한다() {
        System.out.println("아메리카노 제조 완료");
    }

    void 리필한다() {
        System.out.println("아메리카노 리필 완료");
    }
}

// 병음료도 분명 음료이기 때문에 Drink를 상속했다.
// 하지만 병음료는 '리필'이라는 동작을 지원하지 않는다.
class BottledWater extends Drink {

    void 제조한다() {
        System.out.println("생수 제공 완료");
    }

    void 리필한다() {
        throw new RuntimeException("병음료는 리필할 수 없습니다!");
    }
}

public class App {
    public static void main(String[] args) {

        Drink[] drinks = {
            new Americano(),
            new BottledWater()
        };

        for (Drink drink : drinks) {
            drink.제조한다();
            drink.리필한다();   // BottledWater에서 문제 발생
        }

        // Drink라면 리필할 수 있다고 믿었는데
        // 어떤 자식은 그 약속을 지키지 못한다.
        // → 부모 대신 자식을 넣었을 때 프로그램이 깨진다.
        // → LSP 위반
    }
}