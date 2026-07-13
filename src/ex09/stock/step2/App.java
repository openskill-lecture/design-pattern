package ex09.stock.step2;

// 이제 증권사가 알려준다(푸시). 투자자는 안 물어봐도 된다.
// 하지만 증권사가 투자자를 '직접' 필드로 안다 → 강결합.

class Investor {
    private String name;
    Investor(String name) { this.name = name; }

    void 알림받기(int price) {
        System.out.println(name + " : " + price + "원으로 변동됐어요");
    }
}

class StockCenter {
    private Investor investor1;   // 투자자를 직접 박아둔다.
    private Investor investor2;   // 투자자가 늘면? 필드를 또 늘려야 한다.
    private int price;

    void 구독(Investor a, Investor b) {
        this.investor1 = a;
        this.investor2 = b;
    }

    void 시세변동() {
        try { Thread.sleep(3000); } catch (InterruptedException e) { throw new RuntimeException(e); }
        price = 53000;
        System.out.println("[증권사] 시세 변동 완료!");
        investor1.알림받기(price);   // int price가 그대로 흐른다
        investor2.알림받기(price);   // 투자자3이 생기면 여기 또 추가… (증권사를 뜯어고쳐야 한다)
    }
}

public class App {
    public static void main(String[] args) {
        Investor investor1 = new Investor("투자자1");
        Investor investor2 = new Investor("투자자2");

        StockCenter center = new StockCenter();
        center.구독(investor1, investor2);

        // 증권사가 알아서 알려주므로 투자자는 '물어보지 않는다'. (step1 폴링 낭비가 사라졌다)
        new Thread(() -> center.시세변동()).start();

        System.out.println("----------------------------");
        // 그런데 투자자2가 "난 이제 그만 받을래" 하면? 증권사가 하드코딩이라 뺄 방법이 없다.
        System.out.println("투자자2 : (그만 받고 싶은데 뺄 방법이 없네요)");
        // → 투자자 추가도, 구독 취소도 전부 증권사 코드를 고쳐야 가능하다. 강결합.
    }
}
