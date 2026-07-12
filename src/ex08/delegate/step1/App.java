package ex08.delegate.step1;

// 점원은 주문만 받으면 될까? 요리까지 혼자 다 한다.
class Waiter {
    void 주문받기(String menu) {
        System.out.println(menu + " 주문을 받습니다.");

        // 점원이 요리까지 직접 한다. 역할이 너무 많다.
        System.out.println(menu + " 재료를 준비합니다.");
        System.out.println(menu + " 요리를 합니다.");
    }
}

public class App {
    public static void main(String[] args) {
        Waiter waiter = new Waiter();

        waiter.주문받기("김치찌개");   // 주문도 요리도 점원 혼자.

        // 점원 혼자 다 한다. 요리를 넘겨받을 전문가(주방장)가 필요하다.
    }
}
