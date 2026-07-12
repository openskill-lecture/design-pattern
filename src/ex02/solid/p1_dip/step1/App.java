package ex02.solid.p1_dip.step1;

// 계산대가 특정 결제기를 직접 소유한다. 다른 결제기로 바꿀 수 있을까?
class CardReader {
    void 결제한다(int amount) {
        System.out.println("카드로 " + amount + "원 결제");
    }
}

class Counter {
    // 계산대가 카드단말기를 직접 만들어 들고 있다. (구체 클래스에 못박힘)
    private CardReader reader = new CardReader();

    void 계산한다(int amount) {
        reader.결제한다(amount);
    }
}

public class App {
    public static void main(String[] args) {
        Counter counter = new Counter();
        counter.계산한다(4000); // 카드로 4000원 결제

        // 현금결제기로 바꾸고 싶어도? Counter 안의 new CardReader()를 뜯어고쳐야 한다.
        // 계산대(고수준)가 결제기(저수준 구체)에 직접 묶여있다. ← DIP 위반
        // [실습] Payable 인터페이스를 뽑고, 계산대가 그걸 생성자로 주입받게 고쳐보자 → step2
    }
}
