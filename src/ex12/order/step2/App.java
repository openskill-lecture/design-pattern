package ex12.order.step2;

// step2 : 결제를 계약(PayGate)으로 뽑고, '가짜(Mock)'로 주문 로직을 자동 검증한다.
// (Stub 은 ex11 에서 이미 했다. 여기서는 한 걸음 더 — 호출을 '기록'해 검증하는 Mock.)
//
// 왜 AI 프렌들리인가 :
//   - 계약(PayGate) = AI 에게 '무엇을 만들지'를 모호함 없이 알려주는 스펙
//   - 자동 검증(main) = AI 가 짠 코드의 실수를 즉시 잡아낸다  ← 이번엔 '이중 결제'를 잡는다

interface PayGate {
    boolean 결제(int amount);
}

// 가짜(Mock) : 값도 대답하지만, '몇 번 불렸는지'를 기록한다.
// ★ 값만 대답하는 Stub(예: return 1000) 과의 결정적 차이 = 이 count(호출 기록).
class MockPayGate implements PayGate {
    int count = 0;                   // 호출 기록 (행위 검증용)

    public boolean 결제(int amount) {
        count++;                     // 불릴 때마다 기록
        return true;
    }
}

// 버그 : 주문 한 번에 결제를 '두 번' 호출한다. (결제 코드가 실수로 중복됐다)
class OrderService {
    private PayGate payGate;

    OrderService(PayGate payGate) {  // 주입
        this.payGate = payGate;
    }

    boolean 주문(int amount) {
        if (amount <= 0) return false;
        payGate.결제(amount);          // 첫 번째 결제
        return payGate.결제(amount);   // 버그! 두 번째 결제 (이중 결제)
    }
}

public class App {
    public static void main(String[] args) {
        MockPayGate mock = new MockPayGate();
        OrderService service = new OrderService(mock);

        boolean ok = service.주문(1000);

        // 1) 결과값(상태)만 보면? → 성공(true). '통과'처럼 보여 이중 결제를 놓친다.
        System.out.println("주문 결과 : " + (ok ? "성공" : "실패") + "  ← 값만 보면 통과처럼 보인다");

        System.out.println("----------------------------");

        // 2) '몇 번 불렸나'(행위)를 보면? → 2번. 이중 결제를 잡는다. (이게 Mock)
        System.out.println("결제 호출 횟수 : " + mock.count + " (예상 1)");
        System.out.println(mock.count == 1
                ? "[통과]"
                : "[실패] 이중 결제 적발! 결제가 " + mock.count + "번 불렸다.");
    }
}
