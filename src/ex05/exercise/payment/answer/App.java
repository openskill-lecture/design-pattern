package ex05.exercise.payment.answer;

// [연습문제 정답 - 어댑터패턴] 외부 결제 라이브러리를 우리 계산대 규격(Payment)에 맞춘다.

interface Payment {
    void 결제(int amount);
}

class Cash implements Payment {
    public void 결제(int amount) {
        System.out.println("현금 " + amount + "원 결제");
    }
}

// [외부 라이브러리] 우리가 못 고친다. 규격이 다르다 : sendMoney(int)
class KakaoPay {
    void sendMoney(int won) {
        System.out.println("카카오페이 " + won + "원 결제");
    }
}

// [어댑터] Payment 규격을 뒤집어쓰고, 외부 카카오페이를 품는다.
// 계산대가 결제()를 부르면 → 안에서 sendMoney()로 넘긴다. (규격 변환)
class KakaoPayAdapter implements Payment {
    private KakaoPay kakaoPay;

    KakaoPayAdapter(KakaoPay kakaoPay) {
        this.kakaoPay = kakaoPay;
    }

    public void 결제(int amount) {
        kakaoPay.sendMoney(amount);   // 결제() → sendMoney()
    }
}

class Cashier {
    void 계산(Payment payment, int amount) {
        payment.결제(amount);
    }
}

public class App {
    public static void main(String[] args) {
        Cashier cashier = new Cashier();

        cashier.계산(new Cash(), 3000);   // 현금 3000원 결제

        System.out.println("----------------------------");

        // 외부 카카오페이를 어댑터로 감싸면, 계산대 눈엔 그냥 Payment다.
        Payment kakao = new KakaoPayAdapter(new KakaoPay());
        cashier.계산(kakao, 5000);        // 카카오페이 5000원 결제 — 현금과 똑같이 쓴다
    }
}
