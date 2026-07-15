package ex12.order.step2;

// step2 : step1 은 주문이 결제까지 직접 했다 (책임이 안 나뉨). 결제를 계약(PayGate)으로 '분리'한다.
// 주문은 '주문 흐름'만, 결제는 PayGate 가 한다. 진짜 결제 구현(RealPayGate)을 주입한다.
// → 출력(행동)은 step1 과 똑같이 '진짜 결제'다. 바뀐 건 코드 '구조' — 갈아끼울 '이음새'가 생겼다.
//   (이 이음새에 가짜(Mock)를 끼워 테스트하는 건 step3 부터.)

interface PayGate {
    boolean 결제(int amount);
}

// 진짜 결제 : step1 에서 주문 안에 박혀 있던 카드사 통신을 '제 자리'로 옮겼다.
class RealPayGate implements PayGate {
    public boolean 결제(int amount) {
        System.out.println("[진짜] 카드사 승인 요청... " + amount + "원 실제 결제됨");
        return true;
    }
}

// 주문은 결제를 직접 하지 않는다. 주입받은 PayGate 에게 '시킨다'. (책임 분리)
class OrderService {
    private PayGate payGate;

    OrderService(PayGate payGate) {  // 주입
        this.payGate = payGate;
    }

    boolean 주문(int amount) {
        if (amount <= 0) return false;
        return payGate.결제(amount);  // 결제는 PayGate 담당
    }
}

public class App {
    public static void main(String[] args) {
        // 진짜 결제 구현을 끼운다. 출력은 step1 과 같다(진짜 돈). 달라진 건 '구조'다.
        OrderService service = new OrderService(new RealPayGate());

        service.주문(1000);   // [진짜] 결제됨 — 아직 진짜다.
        System.out.println("→ 결제를 분리했다. 이제 이 자리에 가짜를 끼우면 테스트가 된다. (step3)");
    }
}
