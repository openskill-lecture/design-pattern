package ex09.observer.step1;

// 손님은 상품이 언제 들어온 걸 알까? — 계속 물어본다(폴링).

class Mart {
    private String stock = null;          // 아직 상품이 없다.

    public String getStock() {            // 손님이 물어볼 때 꺼내보는 창구
        return stock;
    }

    void 입고() {                          // 상품은 '나중에'(3초 뒤) 들어온다.
        try { Thread.sleep(3000); } catch (InterruptedException e) { throw new RuntimeException(e); }
        stock = "바나나";
        System.out.println("[마트] 바나나 입고 완료!");
    }
}

class Customer {
    private String name;
    Customer(String name) { this.name = name; }

    void 알림받기(String msg) {
        System.out.println(name + " : " + msg + " 확인!");
    }
}

public class App {
    public static void main(String[] args) {
        Mart mart = new Mart();
        Customer cus1 = new Customer("손님1");

        // 마트는 '나중에' 입고된다. 손님을 3초간 굳혀둘 순 없으니 마트는 딴 일꾼(스레드)에게 맡긴다.
        new Thread(() -> mart.입고()).start();

        // 손님(main) : 상품 왔나? 1초마다 '계속' 물어본다.
        while (true) {
            try { Thread.sleep(1000); } catch (InterruptedException e) { throw new RuntimeException(e); }

            if (mart.getStock() != null) {                  // 드디어 들어왔다!
                cus1.알림받기(mart.getStock() + " 들어왔어요");
                break;
            }
            System.out.println("손님1 : 아직 안 왔어요? (또 물어봄)");   // 헛물음 = 낭비!
        }

        // 손님이 계속 물어봐야 안다. 묻는 주기(1초)를 잘못 잡으면 → 헛물음이 더 늘거나, 더 늦게 안다.
    }
}
