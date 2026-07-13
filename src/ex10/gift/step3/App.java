package ex10.gift.step3;

// 데코레이터 패턴 — 포장이 선물을 '감싸서' 기능을 덧붙인다.
// 포장이 하나 늘어도 클래스는 하나만 추가. 런타임에 자유 조합.

interface Gift {                          // 통로(약속)
    void 포장한다();
}

class BasicGift implements Gift {         // 알맹이
    @Override
    public void 포장한다() {
        System.out.println("기본 선물");
    }
}

abstract class GiftDecorator implements Gift {  // 감싸는 포장(데코레이터)
    protected Gift gift;                  // 감싸고 있는 대상
    GiftDecorator(Gift gift) { this.gift = gift; }
}

class BoxGift extends GiftDecorator {
    BoxGift(Gift gift) { super(gift); }
    @Override
    public void 포장한다() {
        gift.포장한다();                   // 감싼 대상 먼저 포장하고
        System.out.println("상자 포장");    // 내 포장을 덧붙인다
    }
}

class WrapGift extends GiftDecorator {
    WrapGift(Gift gift) { super(gift); }
    @Override
    public void 포장한다() {
        gift.포장한다();
        System.out.println("포장지 포장");
    }
}

class RibbonGift extends GiftDecorator {
    RibbonGift(Gift gift) { super(gift); }
    @Override
    public void 포장한다() {
        gift.포장한다();
        System.out.println("리본 장식");
    }
}

public class App {
    public static void main(String[] args) {
        // 기본 선물을 감싸며 포장을 덧붙인다 — 런타임에 원하는 만큼 조합
        Gift all = new RibbonGift(new WrapGift(new BoxGift(new BasicGift())));
        all.포장한다();

        System.out.println("----------------------------");
        // 순서를 바꾸든(포장지 없이 리본만), 포장을 빼든 조합만 바꾸면 끝. 새 클래스가 필요 없다.
        Gift boxRibbonOnly = new RibbonGift(new BoxGift(new BasicGift()));
        boxRibbonOnly.포장한다();
    }
}
