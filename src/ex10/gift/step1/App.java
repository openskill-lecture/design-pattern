package ex10.gift.step1;

// 선물은 기본 하나뿐. 여기에 상자·리본도 같이 씌우고 싶어진다.

class BasicGift {
    void 포장한다() {
        System.out.println("기본 선물");
    }
}

public class App {
    public static void main(String[] args) {
        BasicGift gift = new BasicGift();
        gift.포장한다();

        // 상자도, 리본도 같이 씌우려면? 여기서부터 기능을 늘려야 한다. (다음 step)
    }
}
