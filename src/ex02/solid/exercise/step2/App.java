package ex02.solid.exercise.step2;

/*
 * [연습문제 정답] 주문 접수 채널 - 추상화로 해결
 *
 * OrderChannel 인터페이스를 새로 만들었다. (추상화)
 *   - 각 채널은 이 인터페이스를 구현한다.        → 새 채널 = 클래스 추가만 (OCP)
 *   - OrderTaker는 OrderChannel을 주입받는다.     → 구체 클래스를 모른다 (DIP)
 *
 * 키오스크가 필요하면 KioskOrder 하나만 추가하면 끝. OrderTaker는 손도 안 댄다.
 */

// 새로 만든 추상 (모든 주문 채널의 약속)
interface OrderChannel {
    void 접수한다(String menu);
}

class StoreOrder implements OrderChannel {
    public void 접수한다(String menu) {
        System.out.println("[매장] " + menu + " 주문 접수");
    }
}

class PhoneOrder implements OrderChannel {
    public void 접수한다(String menu) {
        System.out.println("[전화] " + menu + " 주문 접수");
    }
}

class DeliveryAppOrder implements OrderChannel {
    public void 접수한다(String menu) {
        System.out.println("[배달앱] " + menu + " 주문 접수");
    }
}

// 새 채널 = 클래스 추가만. 아래 OrderTaker는 한 줄도 안 고친다.
class KioskOrder implements OrderChannel {
    public void 접수한다(String menu) {
        System.out.println("[키오스크] " + menu + " 주문 접수");
    }
}

class OrderTaker {
    // 어떤 채널인지 모른다. OrderChannel(추상)만 알고, 밖에서 받아 시킨다.
    void 주문한다(OrderChannel channel, String menu) {
        channel.접수한다(menu);
    }
}

public class App {
    public static void main(String[] args) {
        OrderTaker taker = new OrderTaker();

        // 채널을 밖에서 골라 주입한다. if-else가 사라졌다.
        taker.주문한다(new StoreOrder(), "아메리카노");
        taker.주문한다(new PhoneOrder(), "라떼");
        taker.주문한다(new DeliveryAppOrder(), "바닐라라떼");

        System.out.println("----------------------------");

        // 키오스크 추가 = 클래스 하나. OrderTaker는 수정 0줄. ← OCP + DIP
        taker.주문한다(new KioskOrder(), "콜드브루");
    }
}
