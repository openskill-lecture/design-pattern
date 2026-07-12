package ex11.exercise.discount.answer;

// [연습 정답 - Stub] 할인 정책을 계약으로 뽑고, '값을 대답하는' 가짜(Stub)를 끼운다.
// ★ Stub 의 핵심 : 메서드에 return 값이 있다. 물어보면 정해진 답을 준다.
interface DiscountPolicy {
    int discount(int price);
}

// 가짜(Stub) : 테스트를 위해 '1000원 깎아줌'이라고 정해진 값을 대답한다.
class StubDiscountPolicy implements DiscountPolicy {
    public int discount(int price) {
        return 1000;                 // 정해진 값을 대답 (진짜 할인 서버 없이 테스트)
    }
}

class OrderService {
    private DiscountPolicy policy;

    OrderService(DiscountPolicy policy) {   // 주입
        this.policy = policy;
    }

    int 총액(int price) {
        return price - policy.discount(price);
    }
}

public class App {
    public static void main(String[] args) {
        // 진짜 할인 서버 없이, Stub 이 대답한 값으로 총액 로직을 검증한다.
        OrderService service = new OrderService(new StubDiscountPolicy());
        int total = service.총액(10000);
        System.out.println("총액 : " + total);              // 9000

        System.out.println("----------------------------");
        System.out.println(total == 9000 ? "[통과] 10000 - 1000 = 9000" : "[실패]");
        // 검증 포인트 = '리턴된 값'(상태). 이게 Stub 의 쓰임이다.
    }
}
