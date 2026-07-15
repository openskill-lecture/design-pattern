package ex12.order.step4;

// step4 : step3 은 "몇 번 불렀나"(count) 였다. 이번엔 "순서대로 불렀나"를 검증한다.
// 규칙 : 주문은 반드시 '재고확인 → 결제' 순서. 결제를 먼저 하면 없는 재고를 팔아버린다.
// → Mock 이 count 대신 '호출 순서'를 기록한다. (기록 대상은 검증 목적에 따라 달라진다)

interface Store {
    void 재고확인();
    void 결제(int amount);
}

// 가짜(Mock) : 호출을 '순서대로' 기록한다. (step3 의 count 와는 기록 대상이 다르다)
class MockStore implements Store {
    String order = "";               // 무엇을, 어떤 순서로 불렀나

    public void 재고확인() {
        order += "재고확인 ";
    }

    public void 결제(int amount) {
        order += "결제 ";
    }
}

// 버그 : 결제를 먼저 하고 재고확인을 나중에 한다. (순서가 뒤바뀜)
class OrderService {
    private Store store;

    OrderService(Store store) {      // 주입
        this.store = store;
    }

    void 주문(int amount) {
        store.결제(amount);          // 버그! 결제를 먼저 해버린다
        store.재고확인();            // 재고확인이 뒤로 밀렸다
    }
}

public class App {
    public static void main(String[] args) {
        MockStore mock = new MockStore();
        OrderService service = new OrderService(mock);

        service.주문(1000);

        // 행위 검증 : '호출 순서'를 본다. count(몇 번) 로는 절대 못 잡는 버그다.
        System.out.println("호출 순서 : " + mock.order.trim());
        boolean ok = mock.order.trim().equals("재고확인 결제");
        System.out.println(ok
                ? "[통과] 재고확인 → 결제 순서 정상"
                : "[실패] 순서 뒤바뀜 적발! 없는 재고를 팔 뻔했다. (기대: 재고확인 결제)");
    }
}
