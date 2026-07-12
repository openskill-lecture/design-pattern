package ex09.observer.step3;

import java.util.ArrayList;
import java.util.List;

// 마트는 '구독 명단'만 안다. 손님은 통로(인터페이스)로 약속만 지키면 된다.
// 손님을 추가하든 빼든 마트 코드는 그대로. → 옵저버 패턴.

interface Customer {                    // 통로(약속) : "알림 오면 받는다"
    void 알림받기(String msg);
}

class Guest implements Customer {
    private String name;
    Guest(String name) { this.name = name; }

    @Override
    public void 알림받기(String msg) {
        System.out.println(name + " : " + msg);
    }
}

class Mart {
    private List<Customer> customerList = new ArrayList<>();   // 구독 명단

    void 구독(Customer c)     { customerList.add(c); }
    void 구독취소(Customer c) { customerList.remove(c); }

    private void 알림(String msg) {                            // 명단 전체에게 push
        customerList.forEach(c -> c.알림받기(msg));
    }

    void 입고() {
        try { Thread.sleep(3000); } catch (InterruptedException e) { throw new RuntimeException(e); }
        System.out.println("[마트] 포도 입고 완료!");
        알림("포도 들어왔어요");
    }
}

public class App {
    public static void main(String[] args) {
        Customer cus1 = new Guest("손님1");
        Customer cus2 = new Guest("손님2");
        Customer cus3 = new Guest("손님3");

        Mart mart = new Mart();
        mart.구독(cus1);
        mart.구독(cus2);
        mart.구독(cus3);

        mart.구독취소(cus2);      // 손님2 구독취소 — 마트 코드는 안 건드린다!

        new Thread(() -> mart.입고()).start();

        // 손님을 늘려도(구독), 빼도(구독취소) 마트는 그대로.
        // 입고되면 명단에 남은 손님1·손님3만 알림을 받는다.
    }
}
