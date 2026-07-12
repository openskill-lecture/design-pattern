package ex02.solid.p4_lsp.step2;

// 실습아님
// '제조할 수 있는 것'만 Drink다. 만들 수 없는 건 아예 Drink가 아니다.
abstract class Drink {
    abstract void 제조한다();
}

class Americano extends Drink {
    void 제조한다() {
        System.out.println("아메리카노 제조 완료");
    }
}

class Latte extends Drink {
    void 제조한다() {
        System.out.println("라떼 제조 완료");
    }
}

// 모형은 음료(Drink)가 아니라 '전시품'일 뿐. 주문 목록에 낄 수 없다.
class DisplayModel {
    void 전시한다() {
        System.out.println("쇼케이스에 전시 중");
    }
}

public class App {
    public static void main(String[] args) {
        Drink[] orders = { new Americano(), new Latte() };

        for (Drink drink : orders) {
            drink.제조한다(); // 어떤 Drink가 와도 안전하게 제조된다
        }

        System.out.println("----------------------------");

        new DisplayModel().전시한다(); // 모형은 제 자리(전시)에서만 쓴다

        // 부모 자리에 어떤 자식이 와도 약속이 지켜진다. ← LSP
    }
}
