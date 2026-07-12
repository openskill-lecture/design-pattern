package ex02.solid.p3_srp.step2;

// 완성코드 : 책임마다 담당을 나눈다. 각자 자기 이유로만 바뀐다.
class Barista {          // 제조만
    void 제조한다(String drink) {
        System.out.println(drink + " 제조 완료");
    }
}

class Cashier {          // 계산만
    void 계산한다(int amount) {
        System.out.println(amount + "원 결제 완료");
    }
}

class ReceiptPrinter {   // 영수증만
    void 발행한다(String drink, int amount) {
        System.out.println("[영수증] " + drink + " " + amount + "원");
    }
}

public class App {
    public static void main(String[] args) {
        String drink = "아메리카노";
        int amount = 4000;

        new Barista().제조한다(drink);
        new Cashier().계산한다(amount);
        new ReceiptPrinter().발행한다(drink, amount);

        // 영수증 양식이 바뀌면 ReceiptPrinter만 고친다. 다른 담당은 안전하다. ← SRP
    }
}
