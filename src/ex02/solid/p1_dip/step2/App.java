package ex02.solid.p1_dip.step2;

// 완성코드 : 계산대는 "결제된다"는 규격만 안다. 어떤 결제기인지는 밖에서 꽂아준다.
interface Payable {
    void 결제한다(int amount);
}

class CardReader implements Payable {
    public void 결제한다(int amount) {
        System.out.println("카드로 " + amount + "원 결제");
    }
}

class CashRegister implements Payable {
    public void 결제한다(int amount) {
        System.out.println("현금으로 " + amount + "원 결제");
    }
}

class Counter {
    private Payable reader;        // 무엇이 올지 모른다. 규격(Payable)만 안다.

    Counter(Payable reader) {      // 생성자로 결제기를 주입받는다.
        this.reader = reader;
    }

    void 계산한다(int amount) {
        reader.결제한다(amount);
    }
}

public class App {
    public static void main(String[] args) {
        Counter cardCounter = new Counter(new CardReader());
        cardCounter.계산한다(4000);   // 카드로 4000원 결제

        System.out.println("----------------------------");

        // 결제기만 갈아끼운다. 계산대 코드는 한 줄도 안 바뀐다. ← DIP
        Counter cashCounter = new Counter(new CashRegister());
        cashCounter.계산한다(4000);   // 현금으로 4000원 결제
    }
}
