package ex11.exercise.discount.start;

// [연습 - Stub] 시작 : 주문 총액 계산이 '진짜 할인 서버'에 직접 묶여 있다.
// TODO: DiscountPolicy 를 계약(인터페이스)으로 뽑고,
//       '정해진 값을 대답하는' 가짜(Stub)를 끼워 진짜 서버 없이 총액을 테스트해보자.
//       (Stub = 물어보면 정해진 값을 return 해 주는 객체)
class OrderService {
    // 진짜 할인 서버에 매번 물어봐야 한다. 서버 없으면 계산도 테스트도 못 한다.
    int 총액(int price) {
        int discount = 0;   // 진짜가 없어서 일단 0 → 테스트가 무의미하다.
        return price - discount;
    }
}

public class App {
    public static void main(String[] args) {
        OrderService service = new OrderService();
        System.out.println("총액 : " + service.총액(10000));
    }
}
