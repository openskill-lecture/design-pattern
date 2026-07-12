package ex09.observer.step2;

// 이제 마트가 알려준다(푸시). 손님은 안 물어봐도 된다.
// 하지만 마트가 손님을 '직접' 필드로 안다 → 강결합.

class Customer {
    private String name;
    Customer(String name) { this.name = name; }

    void 알림받기(String msg) {
        System.out.println(name + " : " + msg);
    }
}

class Mart {
    private Customer cus1;   // 손님을 직접 박아둔다.
    private Customer cus2;   // 손님이 늘면? 필드를 또 늘려야 한다.

    void 구독(Customer a, Customer b) {
        this.cus1 = a;
        this.cus2 = b;
    }

    void 입고() {
        try { Thread.sleep(3000); } catch (InterruptedException e) { throw new RuntimeException(e); }
        System.out.println("[마트] 딸기 입고 완료!");
        cus1.알림받기("딸기 들어왔어요");   // 하드코딩
        cus2.알림받기("딸기 들어왔어요");   // 손님3이 생기면 여기 또 추가… (마트를 뜯어고쳐야 한다)
    }
}

public class App {
    public static void main(String[] args) {
        Customer cus1 = new Customer("손님1");
        Customer cus2 = new Customer("손님2");

        Mart mart = new Mart();
        mart.구독(cus1, cus2);

        // 마트가 알아서 알려주므로 손님은 '물어보지 않는다'. (step1 폴링 낭비가 사라졌다)
        new Thread(() -> mart.입고()).start();

        System.out.println("----------------------------");
        // 그런데 손님2가 "난 이제 그만 받을래" 하면? 마트가 하드코딩이라 뺄 방법이 없다.
        System.out.println("손님2 : (사실 그만 받고 싶은데... 뺄 방법이 없네요)");
        // → 손님 추가도, 구독 취소도 전부 마트 코드를 고쳐야 가능하다. 강결합.
    }
}
