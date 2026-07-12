package ex02.solid.exercise.step1;

/*
 * [연습문제] 주문 접수 채널 - 유지보수성 낮은 코드 (여기서 시작)
 *
 * 지금은 추상화가 전혀 없다. 구체 클래스(매장/전화/배달앱)만 나열되어 있고,
 * OrderTaker가 문자열로 종류를 if-else 분기하며 직접 new 한다.
 *
 * 문제 : '키오스크 주문'을 새로 추가하려면?
 *   1) KioskOrder 클래스를 만들고
 *   2) OrderTaker의 if-else도 또 뜯어고쳐야 한다.   ← OCP 위반 + DIP 위반
 *
 * 과제(step2) : 인터페이스를 새로 만들고, 각 채널이 그것을 구현하게 하고,
 *   OrderTaker는 그 인터페이스를 주입받게 바꿔라.
 *   그러면 새 채널은 '클래스 추가'만으로 끝난다 (OrderTaker 수정 X).
 */

class StoreOrder {
    void 접수한다(String menu) {
        System.out.println("[매장] " + menu + " 주문 접수");
    }
}

class PhoneOrder {
    void 접수한다(String menu) {
        System.out.println("[전화] " + menu + " 주문 접수");
    }
}

class DeliveryAppOrder {
    void 접수한다(String menu) {
        System.out.println("[배달앱] " + menu + " 주문 접수");
    }
}

class OrderTaker {
    // 종류를 문자열로 받아 직접 분기하고 직접 생성한다. (구체 클래스에 못박힘)
    void 주문한다(String channel, String menu) {
        if (channel.equals("매장")) {
            new StoreOrder().접수한다(menu);
        } else if (channel.equals("전화")) {
            new PhoneOrder().접수한다(menu);
        } else if (channel.equals("배달앱")) {
            new DeliveryAppOrder().접수한다(menu);
        }
    }
}

public class App {
    public static void main(String[] args) {
        OrderTaker taker = new OrderTaker();
        taker.주문한다("매장", "아메리카노");
        taker.주문한다("전화", "라떼");
        taker.주문한다("배달앱", "바닐라라떼");

        // 키오스크를 추가하려면 OrderTaker의 if-else를 또 고쳐야 한다. ← 여기를 리팩터링하라
    }
}
