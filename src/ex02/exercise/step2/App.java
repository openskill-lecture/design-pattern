package ex02.solid.exercise.step2;

// 제공: 모든 주문 채널이 따라야 하는 약속
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

class OrderTaker {
    // TODO 1. String이 아니라 OrderChannel을 보관하세요.
    // private ??? channel;

    // TODO 2. 생성자로 OrderChannel을 주입받으세요.
    // OrderTaker(??? channel) {
    //     this.channel = channel;
    // }

    void 주문한다(String menu) {
        // TODO 4. channel에게 접수하라고 시키세요.
        // channel.???(menu);
    }
}


// TODO 5. 키오스크 주문 클래스를 추가하세요.
// class KioskOrder ...


public class App {
    public static void main(String[] args) {
        // TODO 4. TODO3까지 완료되면 아래 주문한다 주석을 풀고 실행해보세요.
        //OrderTaker storeTaker = new OrderTaker(new StoreOrder());
        //storeTaker.주문한다("아메리카노");

        //OrderTaker phoneTaker = new OrderTaker(new PhoneOrder());
        //phoneTaker.주문한다("라떼");

        //OrderTaker deliveryTaker = new OrderTaker(new DeliveryAppOrder());
        //deliveryTaker.주문한다("바닐라라떼");

        System.out.println("----------------------------");

        // TODO 6. 키오스크 주문을 추가해보세요.
        // OrderTaker kioskTaker = new OrderTaker(new KioskOrder());
        // kioskTaker.주문한다("콜드브루");
    }
}