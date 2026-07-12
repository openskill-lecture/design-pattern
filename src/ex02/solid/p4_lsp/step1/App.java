package ex02.solid.p4_lsp.step1;

// 모든 Drink는 제조할 수 있다고 약속했다. 정말 모든 자식이 지킬까?
abstract class Drink {
    abstract void 제조한다();
}

class Americano extends Drink {
    void 제조한다() {
        System.out.println("아메리카노 제조 완료");
    }
}

// 쇼케이스에 전시하는 플라스틱 모형 음료. Drink를 물려받았지만... 만들 수가 없다!
class DisplayDrink extends Drink {
    void 제조한다() {
        throw new RuntimeException("전시용 모형은 제조할 수 없습니다!");
    }
}

public class App {
    public static void main(String[] args) {
        // 주문 목록엔 그냥 Drink로 담긴다. (부모 타입으로 취급)
        Drink[] orders = { new Americano(), new DisplayDrink() };

        for (Drink drink : orders) {
            drink.제조한다(); // 모형 음료 차례에서 터진다!
        }

        // 자식(DisplayDrink)이 부모(Drink)의 약속(제조 가능)을 못 지킨다. ← LSP 위반
        // [실습] 만들 수 없는 모형은 Drink에서 빼내 별도 타입으로 분리해보자 → step2
    }
}
