package ex03.exercise.start;

// [연습문제 - 전략패턴] 계산대(Cashier)에서 결제수단을 런타임에 갈아끼워라.
//
// 지금 Cashier는 '카드결제' 로직에 딱 붙어 있다.
// 현금/포인트로도 결제할 수 있게, 그리고 나중에 결제수단이 더 생겨도
// Cashier를 고치지 않도록 "전략패턴"으로 바꿔라.
//
// TODO 1) 결제 전략을 추상화한 PaymentStrategy 를 만든다 ( 지불한다(int amount) )
// TODO 2) CardPayment / CashPayment / PointPayment 구체 전략을 만든다
// TODO 3) Cashier 가 PaymentStrategy 를 필드로 보유하고 setter 로 갈아끼우게 한다
// TODO 4) main 에서 같은 cashier 로 결제수단만 바꿔가며 계산해본다

class Cashier {
    // 지금은 '카드'에만 붙어 있다. 이걸 전략으로 뽑아내자.
    void 계산한다(int amount) {
        System.out.println(amount + "원 카드로 결제");
    }
}

public class App {
    public static void main(String[] args) {
        Cashier cashier = new Cashier();
        cashier.계산한다(10000);   // 10000원 카드로 결제

        // 목표 :
        //   cashier.setStrategy(new CashPayment());
        //   cashier.계산한다(5000);
        //   cashier.setStrategy(new PointPayment());
        //   cashier.계산한다(3000);
    }
}
