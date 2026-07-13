package ex10.gift.step1;

// 선물은 기본 하나뿐. 여기에 상자·리본도 같이 씌우고 싶어진다.

class BasicGift {
    int 가격() { return 10000; }
    void 포장한다() {
        System.out.println("기본 선물");
    }
}

public class App {
    public static void main(String[] args) {
        BasicGift gift = new BasicGift();
        gift.포장한다();
        System.out.println("가격 : " + gift.가격() + "원");

        // 상자도, 리본도 같이 씌우면 포장도 늘고 '가격'도 그만큼 더해져야 한다. (다음 step)
    }
}
