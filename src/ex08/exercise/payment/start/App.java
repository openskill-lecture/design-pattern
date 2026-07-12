package ex08.exercise.payment.start;

// [연습문제3 - 시작] 쇼핑몰이 카드 승인까지 직접 한다.
// TODO: 결제(카드 승인)를 PG사(PaymentGateway)에게 대행(위임)해보자.
class Shop {
    void 주문결제(int amount) {
        System.out.println("주문을 접수합니다.");

        // 카드사 통신·승인을 쇼핑몰이 직접 한다. (실무에선 위험하고 복잡)
        System.out.println("카드사에 승인 요청을 보냅니다.");
        System.out.println(amount + "원 결제 승인 완료.");
    }
}

public class App {
    public static void main(String[] args) {
        Shop shop = new Shop();

        shop.주문결제(30000);   // 쇼핑몰이 카드 승인까지 혼자.
    }
}
