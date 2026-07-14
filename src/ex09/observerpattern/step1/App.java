package ex09.observerpattern.step1;

// 손님은 상품이 언제 들어온 걸 알까? — 계속 물어본다(폴링).

class Mart {
    private String stock;          // 아직 상품이 없다.

    public String 재고확인() {     // 손님이 물어볼 때 꺼내보는 창구
        if(stock == null){
            System.out.println("[마트 응답] : 404 not found");
        }else{
            System.out.println("[마트 응답] : 200 ok");
        }
        return stock;
    }

    void 입고(String stock) {
        int delay = (int)(Math.random() * 9000) + 1000;   // 1000~9999ms (약 1~10초). 언제 들어올지 모른다!
        try { Thread.sleep(delay); } catch (InterruptedException e) { throw new RuntimeException(e); }
        this.stock = stock;
        System.out.printf("[마트] %.1f초 만에 입고 완료!%n", delay / 1000.0);
    }
}

class Customer {

    private Mart mart;

    public Customer(Mart mart) {
        this.mart = mart;
    }

    String 물어보기() {
        return mart.재고확인();
    }
}

public class App {
    public static void main(String[] args) {
        Mart mart = new Mart();
        new Thread(() -> mart.입고("딸기")).start();

        Customer cus = new Customer(mart);

        String stock = null;
        while (true) {
            try { Thread.sleep(1000); } catch (InterruptedException e) { throw new RuntimeException(e); }
            stock = cus.물어보기(); 
            System.out.println(stock);
            System.out.println("==========================");
            if(stock != null) break;
        }
    }
}
