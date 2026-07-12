package ex12.order.step1;

// step1 : 주문 로직 안에 '진짜 결제'가 직접 박혀 있다.
// → 테스트 한 번 돌릴 때마다 진짜 돈이 나간다. 자동 테스트를 돌릴 수가 없다.

// 주문 서비스가 진짜 결제(카드사 통신)를 직접 한다.
class OrderService {
    boolean 주문(int amount) {
        if (amount <= 0) return false;

        // 진짜 카드사에 결제 요청 (테스트할 때마다 실제로 돈이 빠진다!)
        System.out.println("[진짜] 카드사 승인 요청... " + amount + "원 실제 결제됨");
        return true;
    }
}

public class App {
    public static void main(String[] args) {
        OrderService service = new OrderService();

        service.주문(1000);   // 테스트인데 진짜 돈이 나갔다.
        System.out.println("→ 진짜 결제에 묶여 있어 자동 테스트도, 병렬 개발도 막혔다.");
    }
}
