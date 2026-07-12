package ex08.delegate.step2;

// 요리는 주방장에게 넘긴다(위임). 점원은 요리법을 몰라도 된다.
class Chef {
    void 요리하기(String menu) {
        System.out.println(menu + " 재료를 준비합니다.");
        System.out.println(menu + " 요리를 합니다.");
    }
}

class Waiter {
    private Chef chef = new Chef();   // 일을 넘길 상대(delegate)를 들고 있다.

    void 주문받기(String menu) {
        System.out.println(menu + " 주문을 받습니다.");

        chef.요리하기(menu);          // 요리는 내가 안 한다. 주방장에게 위임.
    }
}

public class App {
    public static void main(String[] args) {
        Waiter waiter = new Waiter();

        waiter.주문받기("김치찌개");

        System.out.println("----------------------------");

        // 손님 → 점원(Waiter) → 주방장(Chef)
        //          위임자         대리 수행자
        // 점원은 요리법을 몰라도, 요리 요청을 주방장에게 전달만 한다. 이게 델리게이트.
    }
}
