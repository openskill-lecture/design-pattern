package ex08.exercise.payment.answer;

// [연습문제3 정답 - 델리게이트] 결제(카드 승인)는 PG사에게 대행(위임)한다.
class PaymentGateway {
    void 결제한다(int amount) {
        System.out.println("카드사에 승인 요청을 보냅니다.");
        System.out.println(amount + "원 결제 승인 완료.");
    }
}

class Shop {
    private PaymentGateway pg = new PaymentGateway();   // 결제를 넘길 상대(delegate)

    void 주문결제(int amount) {
        System.out.println("주문을 접수합니다.");

        pg.결제한다(amount);                            // 카드 승인은 PG사에게 위임
    }
}

public class App {
    public static void main(String[] args) {
        Shop shop = new Shop();

        shop.주문결제(30000);

        // 쇼핑몰(Shop) → PG사(PaymentGateway) : 결제를 대행(위임).
        // 쇼핑몰은 카드사와 직접 통신하지 않고, 잘하는 전문 업체에 맡긴다.
    }
}
