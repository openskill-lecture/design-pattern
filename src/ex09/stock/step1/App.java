package ex09.stock.step1;

// 투자자는 시세가 바뀐 걸 언제 알까? — 계속 물어본다(폴링).

class StockCenter {
    private Integer price = null;         // 아직 시세가 없다.

    public Integer getPrice() {           // 투자자가 물어볼 때 꺼내보는 창구
        return price;
    }

    void 시세변동() {                      // 시세는 '나중에'(3초 뒤) 바뀐다.
        try { Thread.sleep(3000); } catch (InterruptedException e) { throw new RuntimeException(e); }
        price = 52000;
        System.out.println("[증권사] 시세 변동 완료!");
    }
}

class Investor {
    private String name;
    Investor(String name) { this.name = name; }

    void 알림받기(int price) {
        System.out.println(name + " : " + price + "원으로 변동됐어요 확인!");
    }
}

public class App {
    public static void main(String[] args) {
        StockCenter center = new StockCenter();
        Investor inv1 = new Investor("투자자1");

        // 증권사는 '나중에' 시세가 바뀐다. 투자자를 3초간 굳혀둘 순 없으니 증권사는 딴 일꾼(스레드)에게 맡긴다.
        new Thread(() -> center.시세변동()).start();

        // 투자자(main) : 시세 바뀌었나? 1초마다 '계속' 물어본다.
        while (true) {
            try { Thread.sleep(1000); } catch (InterruptedException e) { throw new RuntimeException(e); }

            if (center.getPrice() != null) {                  // 드디어 바뀌었다!
                inv1.알림받기(center.getPrice());
                break;
            }
            System.out.println("투자자1 : 아직 그대로네요? (또 물어봄)");   // 헛물음 = 낭비!
        }

        // 투자자가 계속 물어봐야 안다. 묻는 주기(1초)를 잘못 잡으면 → 헛물음이 더 늘거나, 더 늦게 안다.
    }
}
