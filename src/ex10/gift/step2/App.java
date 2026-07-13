package ex10.gift.step2;

// 포장 '조합마다' 클래스를 새로 판다 → 포장이 하나 늘 때마다 클래스가 2배씩 폭발.
// 게다가 조합마다 '가격 계산'도 똑같이 복붙된다.

class BasicGift {
    int 가격() { return 10000; }
    void 포장한다() {
        System.out.println("기본 선물");
    }
}

class BoxGift {                            // 기본+상자
    int 가격() { return 10000 + 1000; }
    void 포장한다() {
        System.out.println("기본 선물");
        System.out.println("상자 포장");
    }
}

class RibbonBoxGift {                      // 기본+상자+리본
    int 가격() { return 10000 + 1000 + 500; }
    void 포장한다() {
        System.out.println("기본 선물");
        System.out.println("상자 포장");
        System.out.println("리본 장식");
    }
}

class WrapBoxRibbonGift {                  // 기본+상자+포장지+리본
    int 가격() { return 10000 + 1000 + 2000 + 500; }
    void 포장한다() {
        System.out.println("기본 선물");
        System.out.println("상자 포장");
        System.out.println("포장지 포장");
        System.out.println("리본 장식");
    }
}
// 여기에 '카드'까지 넣으면? BoxCard, RibbonBoxCard, WrapBoxRibbonCard …
// 포장이 하나 늘 때마다 클래스가 2배씩 폭발하고, 가격 계산도 그만큼 복붙된다.

public class App {
    public static void main(String[] args) {
        // 전체 포장 주문 (조합 클래스를 콕 집어 골라야 한다)
        WrapBoxRibbonGift gift = new WrapBoxRibbonGift();
        gift.포장한다();
        System.out.println("가격 : " + gift.가격() + "원");

        System.out.println("----------------------------");
        // "리본만 빼줘" → 정확히 맞는 조합 클래스가 없다.
        // 포장지까지 딸려있는 BoxGift(기본+상자)로 통째로 갈아끼워야 한다. 런타임 변경 불가.
        System.out.println("(리본만 빼고 싶은데... 딱 맞는 조합이 없어 포장지까지 같이 빠지네요)");
        BoxGift onlyBox = new BoxGift();
        onlyBox.포장한다();
        System.out.println("가격 : " + onlyBox.가격() + "원");
        // → 포장을 더하거나 빼려면 매번 새 클래스가 필요하다. 데코레이터로 바꿔보자.
    }
}
