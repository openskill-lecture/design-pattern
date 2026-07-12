package ex05.exercise.payment.start;

// [연습문제 - 어댑터패턴] 외부 결제 라이브러리를 우리 계산대 규격에 맞춰 끼워라.
//
// 우리 계산대(Cashier)는 Payment 규격(결제(int amount))으로만 결제한다.
// 그런데 외부 회사 카카오페이(KakaoPay)는 우리가 못 고치고, 규격도 다르다:
//   결제(int) 가 아니라  sendMoney(int won)  이다.
// 그대로는 계산대에 줄 수 없다 (타입도, 메서드 이름도 안 맞는다).
//
// ── 먼저 이대로 실행해 보라 ──────────────────
// 현금 결제는 잘 된다. 하지만 카카오페이는 계산대에 넣을 수가 없다(아래 주석 참고).
//
// TODO 1) KakaoPayAdapter 를 새로 만든다. Payment 를 구현한다 (implements Payment).
//         - 생성자에서 KakaoPay 를 받아 필드에 품는다.
//         - 결제(int amount) 가 불리면 → 안에서 kakaoPay.sendMoney(amount) 로 넘긴다.
//
// TODO 2) main 에서 KakaoPay 를 어댑터로 감싸 계산대에 넘긴다.
//         - Payment kakao = new KakaoPayAdapter(new KakaoPay());
//         - cashier.계산(kakao, 5000); 이 현금과 똑같이 동작하는지 확인한다.

interface Payment {
    void 결제(int amount);
}

// 우리 현금 결제 : 규격(Payment)에 맞다.
class Cash implements Payment {
    public void 결제(int amount) {
        System.out.println("현금 " + amount + "원 결제");
    }
}

// [외부 라이브러리] 카카오페이. 우리가 못 고친다. 메서드 이름·규격이 다르다.
class KakaoPay {
    void sendMoney(int won) {
        System.out.println("카카오페이 " + won + "원 결제");
    }
}

// 계산대 : Payment 규격으로만 결제한다.
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

        // 외부 카카오페이가 왔다. 하지만 계산대에 못 준다.
        KakaoPay kakao = new KakaoPay();
        // cashier.계산(kakao, 5000);
        // ↑ 주석을 풀면 컴파일 에러! KakaoPay는 Payment가 아니고,
        //   계산대가 부르는 결제()도 없다 (sendMoney() 뿐).

        // 여기서부터 네 코드 : 어댑터로 감싸서 계산대에 넣어라.
    }
}
