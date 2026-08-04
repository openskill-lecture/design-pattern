package ex02.solid.exercise.step1;

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
    private String channel;

    OrderTaker(String channel) {
        this.channel = channel;
    }

    void 주문한다(String menu) {
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
        OrderTaker storeTaker = new OrderTaker("매장");
        storeTaker.주문한다("아메리카노");

        OrderTaker phoneTaker = new OrderTaker("전화");
        phoneTaker.주문한다("라떼");

        OrderTaker deliveryTaker = new OrderTaker("배달앱");
        deliveryTaker.주문한다("바닐라라떼");

        // 키오스크를 추가하려면?
        // 1. KioskOrder 클래스를 만든다.
        // 2. OrderTaker의 if-else를 또 수정해야 한다.
    }
}