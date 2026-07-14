package ex09.exercise.stock.step3;

import java.util.ArrayList;
import java.util.List;

// 증권사는 '구독 명단'만 안다. 투자자는 통로(인터페이스)로 약속만 지키면 된다.
// 투자자를 추가하든 빼든 증권사 코드는 그대로. → 옵저버 패턴.

interface Investor {                    // 통로(약속) : "알림 오면 받는다"
    void 알림받기(int price);
}

class Trader implements Investor {
    private String name;
    Trader(String name) { this.name = name; }

    @Override
    public void 알림받기(int price) {
        System.out.println(name + " : " + price + "원으로 변동됐어요");
    }
}

class StockCenter {
    private List<Investor> investorList = new ArrayList<>();   // 구독 명단
    private int price;

    void 구독(Investor i)     { investorList.add(i); }
    void 구독취소(Investor i) { investorList.remove(i); }

    private void 알림(int price) {                             // 명단 전체에게 push
        investorList.forEach(i -> i.알림받기(price));
    }

    void 시세변동() {
        try { Thread.sleep(3000); } catch (InterruptedException e) { throw new RuntimeException(e); }
        price = 54000;
        System.out.println("[증권사] " + price + "원으로 시세 변동 완료!");
        알림(price);
    }
}

public class App {
    public static void main(String[] args) {
        Investor investor1 = new Trader("투자자1");
        Investor investor2 = new Trader("투자자2");
        Investor investor3 = new Trader("투자자3");

        StockCenter center = new StockCenter();
        center.구독(investor1);
        center.구독(investor2);
        center.구독(investor3);

        center.구독취소(investor2);      // 투자자2 구독취소 — 증권사 코드는 안 건드린다!

        new Thread(() -> center.시세변동()).start();

        // 투자자를 늘려도(구독), 빼도(구독취소) 증권사는 그대로.
        // 시세가 변하면 명단에 남은 투자자1·투자자3만 알림을 받는다.
    }
}
