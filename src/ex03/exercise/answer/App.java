package ex03.exercise.answer;

// [연습문제 정답 - 전략패턴] 계산대는 '결제 전략(추상)'에만 의존한다.
abstract class PaymentStrategy {
    abstract void 지불한다(int amount);
}

class CardPayment extends PaymentStrategy {
    void 지불한다(int amount) { System.out.println(amount + "원 카드로 결제"); }
}

class CashPayment extends PaymentStrategy {
    void 지불한다(int amount) { System.out.println(amount + "원 현금으로 결제"); }
}

class PointPayment extends PaymentStrategy {
    void 지불한다(int amount) { System.out.println(amount + "원 포인트로 결제"); }
}

// [Context] 어떤 결제수단인지 몰라도 된다. 전략에게 시킬 뿐.
class Cashier {
    private PaymentStrategy strategy;

    void setStrategy(PaymentStrategy strategy) {   // 런타임에 결제수단 교체
        this.strategy = strategy;
    }

    void 계산한다(int amount) {
        strategy.지불한다(amount);
    }
}

public class App {
    public static void main(String[] args) {
        Cashier cashier = new Cashier();

        cashier.setStrategy(new CardPayment());
        cashier.계산한다(10000);   // 10000원 카드로 결제

        System.out.println("----------------------------");

        // 같은 cashier, 결제수단만 갈아끼운다
        cashier.setStrategy(new CashPayment());
        cashier.계산한다(5000);    // 5000원 현금으로 결제

        cashier.setStrategy(new PointPayment());
        cashier.계산한다(3000);    // 3000원 포인트로 결제
    }
}
